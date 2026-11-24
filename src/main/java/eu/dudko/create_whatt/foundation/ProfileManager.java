package eu.dudko.create_whatt.foundation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@OnlyIn(Dist.CLIENT)
public class ProfileManager {

    public static final ProfileManager INSTANCE = new ProfileManager();

    private static final String url = "https://sessionserver.mojang.com/session/minecraft/profile/";
    private static final int QUICK_REFETCH_DELAY = 1000 / 2; // 0.5 secs
    private static final int QUICK_REFETCH_LIMIT = 3;
    private static final int FULL_REFETCH_DELAY = 1000 * 60; // 1 min

    private final Map<UUID, Result> results;

    private ProfileManager() {
        results = new ConcurrentHashMap<>();
    }

    public Result getProfileResult(UUID uuid) {
        Result result = results.computeIfAbsent(uuid, this::fetchPlayer);
        if (result.resultType != ResultType.SUCCESS) {
            results.put(uuid, result = fetchPlayer(uuid));
        }
        return result;
    }

    private Result fetchPlayer(UUID uuid) {
        Result oldResult = results.get(uuid);
        if (oldResult != null && !oldResult.canRefetch()) return oldResult;

        int quickAttempts = oldResult == null ? 0 : oldResult.quickAttempts;
        oldResult = Result.fetching(quickAttempts);

        CompletableFuture.runAsync(() -> {
            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest
                        request =
                        HttpRequest.newBuilder(URI.create(url + uuid.toString().replace("-", ""))).GET().build();

                Result result;

                try {
                    String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
                    JsonObject json = JsonParser.parseString(body).getAsJsonObject();
                    String name = json.get("name").getAsString();
                    result = Result.success(name);
                } catch (Exception ignored) {
                    result = Result.failure(quickAttempts);
                }
                results.put(uuid, result);
            }
        });
        return oldResult;
    }

    public enum ResultType {
        SUCCESS,
        FETCHING,
        FAILURE
    }

    public record Result(@Nullable String playerName, ResultType resultType, Instant lastFetch, int quickAttempts) {

        public static Result result(@Nullable String playerName, ResultType resultType, int quickAttempts) {
            return new Result(playerName, resultType, Instant.now(), quickAttempts);
        }

        public static Result success(String playerName) {
            return result(playerName, ResultType.SUCCESS, 0);
        }

        public static Result failure(int quickAttempts) {
            return result(null, ResultType.FAILURE, quickAttempts);
        }

        public static Result fetching(int quickAttempts) {
            return new Result(null, ResultType.FETCHING, Instant.MIN, quickAttempts);
        }

        public Component getName() {
            return switch (resultType) {
                case SUCCESS -> Component.literal(playerName);
                case FETCHING -> Component.translatable("create_whatt.train_map.player_fetching");
                case FAILURE -> Component.translatable("create_whatt.train_map.player_not_found");
            };
        }

        private boolean canQuickRefetch() {
            return quickAttempts < QUICK_REFETCH_LIMIT && lastFetch
                    .plusMillis(QUICK_REFETCH_DELAY)
                    .isBefore(Instant.now());
        }

        private boolean canFullRefetch() {
            return lastFetch.plusMillis(FULL_REFETCH_DELAY).isBefore(Instant.now());
        }

        public boolean canRefetch() {
            return resultType == ResultType.FAILURE && (canQuickRefetch() || canFullRefetch());
        }

    }


}

package eu.dudko.create_whatt.foundation;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;

@OnlyIn(Dist.CLIENT)
public class ProfileManager {

    public static final ProfileManager INSTANCE = new ProfileManager();

    private static final String url = "https://sessionserver.mojang.com/session/minecraft/profile/";

    private final Map<UUID, String> playerNames;
    private final List<UUID> requested;

    private ProfileManager() {
        playerNames = new HashMap<>();
        requested = new ArrayList<>();
    }

    public String getPlayerName(UUID uuid) {
        return playerNames.getOrDefault(uuid, "");
    }

    public String getNameOrFetch(UUID uuid) {
        return getNameOrFetch(uuid, "");
    }

    public String getNameOrFetch(UUID uuid, String fallback) {
        String name = getPlayerName(uuid);
        if (name.isEmpty()) {
            fetchPlayerName(uuid);
            return fallback;
        }
        return name;
    }

    private void fetchPlayerName(UUID uuid) {
        if (requested.contains(uuid)) return;
        CompletableFuture.runAsync(() -> {
            try (HttpClient client = HttpClient.newHttpClient()) {
                HttpRequest
                        request =
                        HttpRequest.newBuilder(URI.create(url + uuid.toString().replace("-", ""))).GET().build();
                try {
                    String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
                    JsonObject json = JsonParser.parseString(body).getAsJsonObject();
                    String name = json.get("name").getAsString();
                    playerNames.put(uuid, name);
                } catch (Exception ignored) {
                }
            }
        });
        requested.add(uuid);
    }


}

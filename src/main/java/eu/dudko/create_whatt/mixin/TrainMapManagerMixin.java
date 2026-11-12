package eu.dudko.create_whatt.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.compat.trainmap.TrainMapManager;
import com.simibubi.create.compat.trainmap.TrainMapSync;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.foundation.utility.CreateLang;
import eu.dudko.create_whatt.config.CClient;
import eu.dudko.create_whatt.config.WhattConfigs;
import eu.dudko.create_whatt.foundation.ProfileManager;
import net.createmod.catnip.lang.LangBuilder;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(TrainMapManager.class)
public abstract class TrainMapManagerMixin {

    @Inject(method = "listTrainDetails",
            at = @At(value = "INVOKE", target = "Ljava/lang/String;isBlank()Z", ordinal = 0))
    private static void create_whatt$offlineOwnerDetails(Train train, CallbackInfoReturnable<List<FormattedText>> cir, @Local(
            name = "trainEntry") TrainMapSync.TrainMapSyncEntry trainEntry, @Local(name = "output") List<FormattedText> output) {

        CClient config = WhattConfigs.client();
        if (!config.showOfflineOwners.get()) return;

        if (trainEntry.ownerName.isBlank()) {
            String ownerName = ProfileManager.INSTANCE.getNameOrFetch(train.owner, "...");
            boolean showIcon = config.showOfflineOwnerIcon.get();
            int color = config.offlineOwnerNameColor.get();

            LangBuilder builder = CreateLang.translate("train_map.train_owned_by", ownerName);
            if (showIcon) builder.add(Component.literal(" \uEF23"));
            builder.color(color);
            builder.addTo(output);
        }
    }

    @Inject(method = "listTrainDetails", at = @At(value = "RETURN"))
    private static void create_whatt$trainUUID(Train train, CallbackInfoReturnable<List<FormattedText>> cir, @Local(name = "output") List<FormattedText> output) {

        CClient config = WhattConfigs.client();
        if (!config.showTrainUuid.get()) return;

        //noinspection DataFlowIssue
        CreateLang.text(train.id.toString()).color(ChatFormatting.DARK_GRAY.getColor()).addTo(output);
    }
}

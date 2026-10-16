package eu.dudko.create_whatt.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.compat.trainmap.TrainMapManager;
import com.simibubi.create.compat.trainmap.TrainMapSync;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.foundation.utility.CreateLang;
import eu.dudko.create_whatt.foundation.ProfileManager;
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
    private static void create_whatt$extraTrainDetails(Train train, CallbackInfoReturnable<List<FormattedText>> cir, @Local(
            name = "trainEntry") TrainMapSync.TrainMapSyncEntry trainEntry, @Local(name = "output") List<FormattedText> output) {

        int red = 0xE0C3C3;

        if (trainEntry.ownerName.isBlank()) {
            String ownerName = ProfileManager.INSTANCE.getNameOrFetch(train.owner, "...");
            CreateLang.translate("train_map.train_owned_by", ownerName).color(red).addTo(output);
        }
    }
}

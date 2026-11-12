package eu.dudko.create_whatt.mixin.compat.xaeros;

import com.llamalad7.mixinextras.sugar.Local;
import com.simibubi.create.content.trains.entity.Train;
import eu.dudko.create_whatt.CreateWhaTTClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.map.gui.GuiMap;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.dropdown.rightclick.RightClickOption;

import java.util.ArrayList;

@Mixin(GuiMap.class)
public abstract class GuiMapMixin implements IRightClickableElement {

    @Inject(method = "getRightClickOptions",
            at = @At(value = "INVOKE",
                    target = "Lxaero/map/util/DistanceUtils;addDistanceRightClickOption(DDDZLxaero/map/gui/IRightClickableElement;Lnet/minecraft/world/entity/Entity;FLxaero/lib/client/config/ClientConfigManager;Ljava/util/ArrayList;)V"))
    public void create_whatt$addTrainOptions(CallbackInfoReturnable<ArrayList<RightClickOption>> cir, @Local(name = "options") ArrayList<RightClickOption> options) {
        final Train hoveredTrain = CreateWhaTTClient.HOVERED_TRAIN;
        if (hoveredTrain != null) {
            options.add(new RightClickOption("gui.xaero_whatt_copy_train_uuid", options.size(), this) {
                @Override
                public void onAction(Screen screen) {
                    Minecraft.getInstance().keyboardHandler.setClipboard(hoveredTrain.id.toString());
                }
            });
        }
    }

}

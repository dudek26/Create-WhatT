package eu.dudko.create_whatt.mixin.compat.xaeros;

import com.simibubi.create.content.trains.entity.Train;
import eu.dudko.create_whatt.CreateWhaTTClient;
import eu.dudko.create_whatt.compat.xaeros.train.TrainMapElement;
import eu.dudko.create_whatt.compat.xaeros.train.TrainMapElementReader;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import xaero.lib.client.gui.widget.dropdown.IDropDownContainer;
import xaero.map.gui.GuiMap;
import xaero.map.gui.IRightClickableElement;
import xaero.map.gui.dropdown.rightclick.GuiRightClickMenu;
import xaero.map.gui.dropdown.rightclick.RightClickOption;

import java.util.ArrayList;

@Mixin(GuiRightClickMenu.class)
public abstract class GuiRightClickMenuMixin {

    @Invoker("<init>")
    static GuiRightClickMenu create_whatt$create(IRightClickableElement target, ArrayList<RightClickOption> options, Screen screen, int x, int y, int w, int titleBackgroundColor, IDropDownContainer container) {
        throw new AssertionError();
    }

    @Inject(method = "getMenu", at = @At("RETURN"), cancellable = true)
    private static void create_whatt$getMenu(IRightClickableElement rightClickable, GuiMap screen, int x, int y, int w, CallbackInfoReturnable<GuiRightClickMenu> cir) {
        if (rightClickable instanceof GuiMap) {
            final Train hoveredTrain = CreateWhaTTClient.getHoveredTrain();

            if (hoveredTrain != null) {
                final TrainMapElement element = new TrainMapElement(hoveredTrain);

                GuiRightClickMenu
                        menu =
                        create_whatt$create(rightClickable,
                                new TrainMapElementReader().getRightClickOptions(element, rightClickable),
                                screen,
                                x,
                                y,
                                w,
                                new TrainMapElementReader().getRightClickTitleBackgroundColor(element),
                                screen);

                cir.setReturnValue(menu);
            }
        }
    }

}

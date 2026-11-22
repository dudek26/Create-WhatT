package eu.dudko.create_whatt.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xaero.map.gui.GuiMap;

@Mixin(GuiMap.class)
public interface GuiMapAccessor {

    @Accessor
    String getLastViewedMultiworldId();

}

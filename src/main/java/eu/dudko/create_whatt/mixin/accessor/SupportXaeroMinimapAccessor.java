package eu.dudko.create_whatt.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.map.mods.SupportXaeroMinimap;

@Mixin(SupportXaeroMinimap.class)
public interface SupportXaeroMinimapAccessor {

    @Accessor
    WaypointWorld getRightClickWaypointWorld();

}

package eu.dudko.create_whatt.compat.xaeros;

import eu.dudko.create_whatt.compat.xaeros.train_tracker.TrainMapElement;
import eu.dudko.create_whatt.foundation.TrainIndexColors;
import eu.dudko.create_whatt.mixin.accessor.GuiMapAccessor;
import eu.dudko.create_whatt.mixin.accessor.SupportXaeroMinimapAccessor;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import xaero.common.XaeroMinimapSession;
import xaero.common.minimap.waypoints.WaypointWorld;
import xaero.common.minimap.waypoints.WaypointsManager;
import xaero.hud.minimap.waypoint.WaypointColor;
import xaero.hud.minimap.waypoint.WaypointPurpose;
import xaero.map.gui.GuiMap;
import xaero.map.mods.SupportMods;
import xaero.map.mods.gui.Waypoint;

public class SupportXaeroMinimapExtension {

    @SuppressWarnings("deprecation")
    public WaypointWorld getTrainWaypointWorld(GuiMap guiMap, TrainMapElement element) {
        ResourceKey<Level> dim = element.getFirstDimension(false);
        if (dim == null) {
            return ((SupportXaeroMinimapAccessor) SupportMods.xaeroMinimap).getRightClickWaypointWorld();
        }
        boolean multiplayer = guiMap.getMapProcessor().getMapWorld().isMultiplayer();
        String multiworldId = ((GuiMapAccessor) guiMap).getLastViewedMultiworldId();
        XaeroMinimapSession minimapSession = XaeroMinimapSession.getCurrentSession();
        WaypointsManager waypointsManager = minimapSession.getWaypointsManager();
        String
                containerId =
                waypointsManager.getAutoRootContainerID() + "/" + waypointsManager.getDimensionDirectoryName(dim);
        String mapBasedMW = !multiplayer ? "waypoints" : multiworldId;
        return waypointsManager.getWorld(containerId, mapBasedMW);
    }

    public void shareTrainLocation(GuiMap guiMap, TrainMapElement element) {
        if (element.getTrainSyncEntry().isEmpty()) return;

        int x = (int) Math.floor(element.getX(false));
        int y = (int) Math.floor(element.getY(false));
        int z = (int) Math.floor(element.getZ(false));

        xaero.common.minimap.waypoints.Waypoint
                minimapLocationWaypoint =
                new xaero.common.minimap.waypoints.Waypoint(x,
                        y,
                        z,
                        "Train: " + element.getTrainNameString(),
                        "T",
                        WaypointColor.values()[TrainIndexColors.toWaypointColorIndex(element.train().mapColorIndex)],
                        WaypointPurpose.NORMAL,
                        false,
                        true);
        Waypoint locationWaypoint = SupportMods.xaeroMinimap.convertWaypoint(minimapLocationWaypoint, false, "", 1.0);
        SupportMods.xaeroMinimap.shareWaypoint(locationWaypoint, guiMap, getTrainWaypointWorld(guiMap, element));
    }

}

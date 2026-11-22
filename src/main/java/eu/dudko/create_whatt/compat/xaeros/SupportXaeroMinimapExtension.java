package eu.dudko.create_whatt.compat.xaeros;

import eu.dudko.create_whatt.compat.xaeros.train_tracker.TrainMapElement;
import eu.dudko.create_whatt.foundation.TrainIndexColors;
import eu.dudko.create_whatt.mixin.compat.xaeros.ISupportXaeroMinimap;
import xaero.map.gui.GuiMap;
import xaero.map.mods.SupportMods;
import xaero.map.mods.gui.Waypoint;

public class SupportXaeroMinimapExtension {

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
                        TrainIndexColors.toWaypointColorIndex(element.train().mapColorIndex),
                        0,
                        false,
                        true);
        Waypoint locationWaypoint = SupportMods.xaeroMinimap.convertWaypoint(minimapLocationWaypoint, false, "", 1.0);
        SupportMods.xaeroMinimap.shareWaypoint(locationWaypoint,
                guiMap,
                ((ISupportXaeroMinimap) SupportMods.xaeroMinimap).getRightClickWaypointWorld());
    }

}

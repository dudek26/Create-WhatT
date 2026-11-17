package eu.dudko.create_whatt.compat.xaeros;

import eu.dudko.create_whatt.compat.xaeros.train_tracker.TrainTrackerMapElementRenderer;
import eu.dudko.create_whatt.compat.xaeros.train_tracker.TrainTrackerMenuRenderer;

public class WorldMapExtension {
    public static TrainTrackerMapElementRenderer trackedTrainsMapElementRenderer;
    public static TrainTrackerMenuRenderer trackedTrainsMenuRenderer;

    public static void init() {
        trackedTrainsMapElementRenderer = TrainTrackerMapElementRenderer.Builder.begin().build();
        trackedTrainsMenuRenderer =
                TrainTrackerMenuRenderer.Builder.begin().setRenderer(trackedTrainsMapElementRenderer).build();
    }
}

package eu.dudko.create_whatt.compat.xaeros;

import eu.dudko.create_whatt.compat.xaeros.train.TrainMapElementRenderer;
import eu.dudko.create_whatt.compat.xaeros.train.TrainMenuRenderer;

public class WorldMapExtension {
    public static TrainMapElementRenderer trainsMapElementRenderer;
    public static TrainMenuRenderer trainsMenuRenderer;

    public static void init() {
        trainsMapElementRenderer = TrainMapElementRenderer.Builder.begin().build();
        trainsMenuRenderer = TrainMenuRenderer.Builder.begin().setRenderer(trainsMapElementRenderer).build();
    }
}

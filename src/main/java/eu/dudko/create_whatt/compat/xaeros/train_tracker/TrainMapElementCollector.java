package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import com.simibubi.create.CreateClient;

public class TrainMapElementCollector {

    public static Iterable<TrainMapElement> getElements() {
        return CreateClient.RAILWAYS.trains.values().stream().map(TrainMapElement::new).toList();
    }
}

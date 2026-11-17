package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import com.simibubi.create.CreateClient;

public class TrainTrackerMapElementCollector {

    public static Iterable<TrainTrackerMapElement> getElements() {
        return CreateClient.RAILWAYS.trains.values().stream().map(TrainTrackerMapElement::new).toList();
    }
}

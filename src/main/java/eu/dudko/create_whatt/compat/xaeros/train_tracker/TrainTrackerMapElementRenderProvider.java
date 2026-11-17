package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import xaero.map.element.MapElementRenderProvider;
import xaero.map.element.render.ElementRenderLocation;

import java.util.Iterator;

public class TrainTrackerMapElementRenderProvider<C> extends MapElementRenderProvider<TrainTrackerMapElement, C> {
    private Iterator<TrainTrackerMapElement> iterator;

    public TrainTrackerMapElementRenderProvider() {
    }

    @Override
    public void begin(ElementRenderLocation location, C context) {
        this.iterator = TrainTrackerMapElementCollector.getElements().iterator();
    }

    @Override
    public boolean hasNext(ElementRenderLocation location, C context) {
        return this.iterator != null && this.iterator.hasNext();
    }

    @Override
    public TrainTrackerMapElement getNext(ElementRenderLocation location, C context) {
        return this.iterator.next();
    }

    @Deprecated
    @Override
    public TrainTrackerMapElement setupContextAndGetNext(ElementRenderLocation location, C context) {
        return this.getNext(location, context);
    }

    @Override
    public void end(ElementRenderLocation location, C context) {
        this.iterator = null;
    }

    @Deprecated
    @Override
    public void begin(int location, C context) {
        this.begin(ElementRenderLocation.fromIndex(location), context);
    }

    @Deprecated
    @Override
    public boolean hasNext(int location, C context) {
        return this.hasNext(ElementRenderLocation.fromIndex(location), context);
    }

    @Deprecated
    @Override
    public TrainTrackerMapElement getNext(int location, C context) {
        return this.getNext(ElementRenderLocation.fromIndex(location), context);
    }

    @Deprecated
    @Override
    public TrainTrackerMapElement setupContextAndGetNext(int location, C context) {
        return this.setupContextAndGetNext(ElementRenderLocation.fromIndex(location), context);
    }

    @Deprecated
    @Override
    public void end(int location, C context) {
        this.end(ElementRenderLocation.fromIndex(location), context);
    }
}

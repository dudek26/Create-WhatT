package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import com.simibubi.create.CreateClient;
import xaero.map.element.MapElementRenderProvider;
import xaero.map.element.render.ElementRenderLocation;

import java.util.Iterator;

public class TrainMapElementRenderProvider<C> extends MapElementRenderProvider<TrainMapElement, C> {
    private Iterator<TrainMapElement> iterator;

    public TrainMapElementRenderProvider() {
    }

    @Override
    public void begin(ElementRenderLocation location, C context) {
        this.iterator = CreateClient.RAILWAYS.trains.values().stream().map(TrainMapElement::new).toList().iterator();
    }

    @Override
    public boolean hasNext(ElementRenderLocation location, C context) {
        return this.iterator != null && this.iterator.hasNext();
    }

    @Override
    public TrainMapElement getNext(ElementRenderLocation location, C context) {
        return this.iterator.next();
    }

    @Deprecated
    @Override
    public TrainMapElement setupContextAndGetNext(ElementRenderLocation location, C context) {
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
    public TrainMapElement getNext(int location, C context) {
        return this.getNext(ElementRenderLocation.fromIndex(location), context);
    }

    @Deprecated
    @Override
    public TrainMapElement setupContextAndGetNext(int location, C context) {
        return this.setupContextAndGetNext(ElementRenderLocation.fromIndex(location), context);
    }

    @Deprecated
    @Override
    public void end(int location, C context) {
        this.end(ElementRenderLocation.fromIndex(location), context);
    }
}

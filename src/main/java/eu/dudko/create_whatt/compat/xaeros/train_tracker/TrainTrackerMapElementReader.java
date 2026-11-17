package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import net.minecraft.client.Minecraft;
import xaero.map.element.MapElementReader;

public class TrainTrackerMapElementReader extends MapElementReader<TrainTrackerMapElement, TrainTrackerMapElementRenderContext, TrainTrackerMapElementRenderer> {

    @Override
    public boolean isHidden(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context) {
        return true;
    }

    public boolean isSameDimension(TrainTrackerMapElement element) {
        return element.isInDimension(Minecraft.getInstance().level.dimension());
    }

    @Override
    public double getRenderX(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public double getRenderZ(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxLeft(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxRight(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxTop(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxBottom(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxLeft(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxRight(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxTop(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxBottom(TrainTrackerMapElement element, TrainTrackerMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getLeftSideLength(TrainTrackerMapElement element, Minecraft var2) {
        return 0;
    }

    @Override
    public String getMenuName(TrainTrackerMapElement element) {
        return element.getTrainNameString();
    }

    @Override
    public String getFilterName(TrainTrackerMapElement element) {
        return this.getMenuName(element);
    }

    @Override
    public int getMenuTextFillLeftPadding(TrainTrackerMapElement element) {
        return 0;
    }

    @Override
    public int getRightClickTitleBackgroundColor(TrainTrackerMapElement element) {
        return 0;
    }

    @Override
    public boolean shouldScaleBoxWithOptionalScale() {
        return true;
    }

}

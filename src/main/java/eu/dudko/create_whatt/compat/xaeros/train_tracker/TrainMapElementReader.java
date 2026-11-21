package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import xaero.map.element.MapElementReader;
import xaero.map.world.MapDimension;
import xaero.map.world.MapWorld;

public class TrainMapElementReader extends MapElementReader<TrainMapElement, TrainMapElementRenderContext, TrainMapElementRenderer> {

    @Override
    public boolean isHidden(TrainMapElement element, TrainMapElementRenderContext context) {
        return true;
    }

    public boolean isSameDimension(TrainMapElement element, MapWorld mapWorld) {
        MapDimension currentDimension = mapWorld.getCurrentDimension();
        MapDimension trainDimension = mapWorld.getDimension(element.getDimensions().getFirst());
        return currentDimension == trainDimension;
    }

    @Override
    public double getRenderX(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public double getRenderZ(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxLeft(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxRight(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxTop(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getInteractionBoxBottom(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxLeft(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxRight(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxTop(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getRenderBoxBottom(TrainMapElement element, TrainMapElementRenderContext context, float partialTicks) {
        return 0;
    }

    @Override
    public int getLeftSideLength(TrainMapElement element, Minecraft mc) {
        return 9 + mc.font.width(I18n.get(element.getTrainNameString()));
    }

    @Override
    public String getMenuName(TrainMapElement element) {
        return element.getTrainNameString();
    }

    @Override
    public String getFilterName(TrainMapElement element) {
        return this.getMenuName(element);
    }

    @Override
    public int getMenuTextFillLeftPadding(TrainMapElement element) {
        return 0;
    }

    @Override
    public int getRightClickTitleBackgroundColor(TrainMapElement element) {
        return 0;
    }

    @Override
    public boolean shouldScaleBoxWithOptionalScale() {
        return true;
    }

}

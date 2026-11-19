package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import com.simibubi.create.Create;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import xaero.map.element.MapElementMenuRenderer;
import xaero.map.element.MapElementRenderProvider;
import xaero.map.element.render.ElementRenderer;

public class TrainTrackerMenuRenderer extends MapElementMenuRenderer<TrainTrackerMapElement, TrainTrackerMenuRenderContext> {

    public static final int TRAINS_BUTTON_X = 22;
    public static final int TRAINS_BUTTON_W = 20;
    public static final int TRAINS_BUTTON_H = 20;
    public static final ResourceLocation TRAINS_ICON_TEXTURE = Create.asResource("textures/gui/assemble.png");
    public static final int TRAINS_ICON_TEXTURE_WIDTH = 19;
    public static final int TRAINS_ICON_TEXTURE_HEIGHT = 9;
    public static final int TRAINS_ICON_TEXTURE_X = 2;
    public static final int TRAINS_ICON_TEXTURE_Y = 205;
    private final TrainTrackerMapElementRenderer renderer;

    protected TrainTrackerMenuRenderer(TrainTrackerMapElementRenderer renderer, TrainTrackerMenuRenderContext context, MapElementRenderProvider<TrainTrackerMapElement, TrainTrackerMenuRenderContext> provider) {
        super(context, provider);
        this.renderer = renderer;
    }

    @Override
    protected void beforeMenuRender() {

    }

    @Override
    protected void afterMenuRender() {

    }

    @Override
    protected ElementRenderer<? super TrainTrackerMapElement, ?, ?> getRenderer(TrainTrackerMapElement element) {
        return this.renderer;
    }

    @Override
    public int menuStartPos(int height) {
        return height - 200;
    }

    @Override
    public int menuSearchPadding() {
        return 1;
    }

    @Override
    public void renderInMenu(TrainTrackerMapElement var1, GuiGraphics var2, Screen var3, int var4, int var5, double var6, boolean var8, boolean var9, Minecraft var10, boolean var11, int var12) {

    }

    @Override
    protected String getFilterPlaceholder() {
        return "gui.xaero_whatt_filter_trains_by_name";
    }

    @Override
    protected void beforeFiltering() {

    }

    public boolean canJumpTo(TrainTrackerMapElement element) {
        return this.renderer.getReader().isSameDimension(element);
    }

    public static final class Builder {
        private TrainTrackerMapElementRenderer renderer;

        private Builder() {
        }

        private Builder setDefault() {
            this.setRenderer(null);
            return this;
        }

        public Builder setRenderer(TrainTrackerMapElementRenderer renderer) {
            this.renderer = renderer;
            return this;
        }

        public TrainTrackerMenuRenderer build() {
            if (this.renderer == null) {
                throw new IllegalStateException();
            }
            else {
                return new TrainTrackerMenuRenderer(this.renderer,
                        new TrainTrackerMenuRenderContext(),
                        new TrainTrackerMapElementRenderProvider<>());
            }
        }

        public static Builder begin() {
            return new Builder().setDefault();
        }
    }
}

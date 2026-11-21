package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import eu.dudko.create_whatt.CreateWhaTT;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import xaero.map.element.MapElementMenuRenderer;
import xaero.map.element.MapElementRenderProvider;
import xaero.map.element.render.ElementRenderer;
import xaero.map.world.MapWorld;

public class TrainMenuRenderer extends MapElementMenuRenderer<TrainMapElement, TrainMenuRenderContext> {

    public static final int TRAINS_BUTTON_X = 20;
    public static final int TRAINS_BUTTON_W = 20;
    public static final int TRAINS_BUTTON_H = 20;
    public static final ResourceLocation TRAINS_ICON_TEXTURE = CreateWhaTT.id("textures/gui/xaero.png");
    public static final int TRAINS_ICON_TEXTURE_WIDTH = 16;
    public static final int TRAINS_ICON_TEXTURE_HEIGHT = 11;
    public static final int TRAINS_ICON_TEXTURE_X = 0;
    public static final int TRAINS_ICON_TEXTURE_Y = 0;

    private final TrainIconRenderer iconRenderer;
    private final TrainMapElementRenderer renderer;

    protected TrainMenuRenderer(TrainMapElementRenderer renderer, TrainMenuRenderContext context, MapElementRenderProvider<TrainMapElement, TrainMenuRenderContext> provider, TrainIconRenderer iconRenderer) {
        super(context, provider);
        this.renderer = renderer;
        this.iconRenderer = iconRenderer;
    }

    @Override
    protected void beforeMenuRender() {

    }

    @Override
    protected void afterMenuRender() {

    }

    @Override
    protected ElementRenderer<? super TrainMapElement, ?, ?> getRenderer(TrainMapElement element) {
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
    public void renderInMenu(TrainMapElement element, GuiGraphics guiGraphics, Screen gui, int mouseX, int mouseY, double scale, boolean enabled, boolean hovered, Minecraft mc, boolean pressed, int textX) {
        this.iconRenderer.renderIcon(guiGraphics, element.train());
    }

    @Override
    protected String getFilterPlaceholder() {
        return "gui.xaero_whatt_filter_trains_by_name";
    }

    @Override
    protected void beforeFiltering() {

    }

    public boolean canJumpTo(TrainMapElement element, MapWorld mapWorld) {
        return this.renderer.getReader().isSameDimension(element, mapWorld);
    }

    public static final class Builder {
        private TrainMapElementRenderer renderer;

        private Builder() {
        }

        private Builder setDefault() {
            this.setRenderer(null);
            return this;
        }

        public Builder setRenderer(TrainMapElementRenderer renderer) {
            this.renderer = renderer;
            return this;
        }

        public TrainMenuRenderer build() {
            if (this.renderer == null) {
                throw new IllegalStateException();
            }
            else {
                return new TrainMenuRenderer(this.renderer,
                        new TrainMenuRenderContext(),
                        new TrainMapElementRenderProvider<>(),
                        new TrainIconRenderer());
            }
        }

        public static Builder begin() {
            return new Builder().setDefault();
        }
    }
}

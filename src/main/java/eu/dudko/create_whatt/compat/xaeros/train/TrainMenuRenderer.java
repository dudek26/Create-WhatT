package eu.dudko.create_whatt.compat.xaeros.train;

import com.mojang.blaze3d.vertex.PoseStack;
import eu.dudko.create_whatt.CreateWhaTT;
import eu.dudko.create_whatt.CreateWhaTTClient;
import eu.dudko.create_whatt.compat.xaeros.GuiMapExpansion;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Renderable;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;
import xaero.map.element.MapElementMenuRenderer;
import xaero.map.element.MapElementRenderProvider;
import xaero.map.element.render.ElementReader;
import xaero.map.element.render.ElementRenderer;
import xaero.map.world.MapWorld;

import java.util.List;

public class TrainMenuRenderer extends MapElementMenuRenderer<TrainMapElement, TrainMenuRenderContext> {

    public static final int TRAINS_BUTTON_X = 20;
    public static final int TRAINS_BUTTON_W = 20;
    public static final int TRAINS_BUTTON_H = 20;
    public static final ResourceLocation TRAINS_ICON_TEXTURE = CreateWhaTT.id("textures/gui/xaero.png");
    public static final int TRAINS_ICON_TEXTURE_WIDTH = 16;
    public static final int TRAINS_ICON_TEXTURE_HEIGHT = 11;
    public static final int TRAINS_ICON_TEXTURE_X = 0;
    public static final int TRAINS_ICON_TEXTURE_Y = 0;
    public static final String TRAIN_WARNING_ICON = "<!> ";

    private final TrainIconRenderer iconRenderer;
    private final TrainMapElementRenderer renderer;

    private @Nullable TrainMapElement hoveredElement;

    protected TrainMenuRenderer(TrainMapElementRenderer renderer, TrainMenuRenderContext context, MapElementRenderProvider<TrainMapElement, TrainMenuRenderContext> provider, TrainIconRenderer iconRenderer) {
        super(context, provider);
        this.renderer = renderer;
        this.iconRenderer = iconRenderer;
    }

    @Override
    protected void beforeMenuRender() {
        hoveredElement = null;
    }

    @Override
    protected void afterMenuRender() {
        if (hoveredElement != null) {
            CreateWhaTTClient.HOVERED_MENU_TRAIN = hoveredElement.train();
        }
        else {
            CreateWhaTTClient.HOVERED_MENU_TRAIN = null;
        }
    }

    @Override
    protected ElementRenderer<? super TrainMapElement, ?, ?> getRenderer(TrainMapElement element) {
        return this.renderer;
    }

    @Override
    public int menuStartPos(int height) {
        return GuiMapExpansion.MENU_Y - 5 - Math.min(GuiMapExpansion.COLUMN, 1) * TrainMenuRenderer.TRAINS_BUTTON_H;
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

    @Override
    public <O> void renderMenuElement(ElementReader<O, ?, ?> reader, O element, GuiGraphics guiGraphics, Screen gui, int x, int y, int mouseX, int mouseY, double scale, boolean enabled, boolean hovered, Minecraft mc, boolean pressed) {
        super.renderMenuElement(reader,
                element,
                guiGraphics,
                gui,
                x,
                y,
                mouseX,
                mouseY,
                scale,
                enabled,
                hovered,
                mc,
                pressed);
        if (!(element instanceof TrainMapElement trainElement)) return;
        if (hovered) {
            hoveredElement = trainElement;
        }

        if (!trainElement.hasWarning()) return;

        PoseStack matrixStack = guiGraphics.pose();
        matrixStack.pushPose();
        if (hovered) {
            matrixStack.translate(pressed ? 1.0F : 2.0F, 0.0F, 0.0F);
        }

        matrixStack.translate(x, y, 0.0F);
        matrixStack.scale((float) scale, (float) scale, 1.0F);
        matrixStack.translate(-4.0F, -4.0F, 0.0F);

        String warning = TRAIN_WARNING_ICON;
        String name = reader.getMenuName(element);
        int len = mc.font.width(name) + mc.font.width(warning);
        int textX = -3 - len;
        guiGraphics.drawString(mc.font, warning, textX, 0, 0xFFFFAD60);

        matrixStack.popPose();
    }

    public static int[] findTrainsButtonPos(int width, int height, List<Renderable> renderables) {
        int x = 0;
        int y = 0;
        int i;
        for (i = 0; i < 3; i++) {
            x = width - i * 20;
            y = height;
            for (Renderable renderable : renderables) {
                if (renderable instanceof Button button
                        && button.getX() == x - TRAINS_BUTTON_W
                        && button.getY() >= height / 4) {
                    y = Math.min(y, button.getY());
                }
            }
            if (y >= height / 2) {
                break;
            }
        }
        return new int[]{x - TRAINS_BUTTON_X, Math.max(0, y - TRAINS_BUTTON_H), i};
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

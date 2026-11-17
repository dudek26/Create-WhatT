package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.TextureManager;
import xaero.map.element.MapElementRenderProvider;
import xaero.map.element.MapElementRenderer;
import xaero.map.element.render.ElementRenderInfo;
import xaero.map.graphics.renderer.multitexture.MultiTextureRenderTypeRendererProvider;

public class TrainTrackerMapElementRenderer extends MapElementRenderer<TrainTrackerMapElement, TrainTrackerMapElementRenderContext, TrainTrackerMapElementRenderer> {

    protected TrainTrackerMapElementRenderer(TrainTrackerMapElementRenderContext context, MapElementRenderProvider<TrainTrackerMapElement, TrainTrackerMapElementRenderContext> provider, TrainTrackerMapElementReader reader) {
        super(context, provider, reader);
    }

    @Override
    @Deprecated
    public TrainTrackerMapElementReader getReader() {
        return (TrainTrackerMapElementReader) super.getReader();
    }

    @Override
    public void preRender(ElementRenderInfo renderInfo, MultiBufferSource.BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow) {
        super.preRender(renderInfo, vanillaBufferSource, rendererProvider, shadow);
    }

    @Override
    public void postRender(ElementRenderInfo renderInfo, MultiBufferSource.BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean shadow) {
        super.postRender(renderInfo, vanillaBufferSource, rendererProvider, shadow);
    }

    @Override
    public void renderElementShadow(TrainTrackerMapElement element, boolean hovered, float optionalScale, double partialX, double partialY, ElementRenderInfo renderInfo, GuiGraphics guiGraphics, MultiBufferSource.BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider) {
        super.renderElementShadow(element,
                hovered,
                optionalScale,
                partialX,
                partialY,
                renderInfo,
                guiGraphics,
                vanillaBufferSource,
                rendererProvider);
    }

    @Override
    public boolean renderElement(TrainTrackerMapElement element, boolean hovered, double optionalDepth, float optionalScale, double partialX, double partialY, ElementRenderInfo renderInfo, GuiGraphics guiGraphics, MultiBufferSource.BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider) {
        return super.renderElement(element,
                hovered,
                optionalDepth,
                optionalScale,
                partialX,
                partialY,
                renderInfo,
                guiGraphics,
                vanillaBufferSource,
                rendererProvider);
    }

    @Deprecated
    @Override
    public void beforeRender(int location, Minecraft mc, GuiGraphics guiGraphics, double cameraX, double cameraZ, double mouseX, double mouseZ, float brightness, double scale, double screenSizeBasedScale, TextureManager textureManager, Font fontRenderer, MultiBufferSource.BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean pre) {
        this.preRender(null, vanillaBufferSource, rendererProvider, pre);
    }

    @Deprecated
    @Override
    public void afterRender(int location, Minecraft mc, GuiGraphics guiGraphics, double cameraX, double cameraZ, double mouseX, double mouseZ, float brightness, double scale, double screenSizeBasedScale, TextureManager textureManager, Font fontRenderer, MultiBufferSource.BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, boolean pre) {
        this.postRender(null, vanillaBufferSource, rendererProvider, pre);
    }

    @Deprecated
    public void renderElementPre(int location, TrainTrackerMapElement element, boolean hovered, Minecraft mc, GuiGraphics guiGraphics, double cameraX, double cameraZ, double mouseX, double mouseZ, float brightness, double scale, double screenSizeBasedScale, TextureManager textureManager, Font fontRenderer, MultiBufferSource.BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, float optionalScale, double partialX, double partialY, boolean cave, float partialTicks) {
        this.renderElementShadow(element,
                hovered,
                optionalScale,
                partialX,
                partialY,
                null,
                guiGraphics,
                vanillaBufferSource,
                rendererProvider);
    }

    @Deprecated
    public boolean renderElement(int location, TrainTrackerMapElement element, boolean hovered, Minecraft mc, GuiGraphics guiGraphics, double cameraX, double cameraZ, double mouseX, double mouseZ, float brightness, double scale, double screenSizeBasedScale, TextureManager textureManager, Font fontRenderer, MultiBufferSource.BufferSource vanillaBufferSource, MultiTextureRenderTypeRendererProvider rendererProvider, int elementIndex, double optionalDepth, float optionalScale, double partialX, double partialY, boolean cave, float partialTicks) {
        return this.renderElement(element,
                hovered,
                optionalDepth,
                optionalScale,
                partialX,
                partialY,
                null,
                guiGraphics,
                vanillaBufferSource,
                rendererProvider);
    }

    @Override
    public boolean shouldRender(int var1, boolean var2) {
        return true;
    }

    @Override
    public int getOrder() {
        return 199;
    }

    public static final class Builder {
        private Builder() {
        }

        private Builder setDefault() {
            return this;
        }

        public TrainTrackerMapElementRenderer build() {
            return new TrainTrackerMapElementRenderer(new TrainTrackerMapElementRenderContext(),
                    new TrainTrackerMapElementRenderProvider<>(),
                    new TrainTrackerMapElementReader());
        }

        public static Builder begin() {
            return new Builder().setDefault();
        }
    }
}

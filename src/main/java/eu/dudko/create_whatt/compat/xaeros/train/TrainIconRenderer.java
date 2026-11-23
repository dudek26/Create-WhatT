package eu.dudko.create_whatt.compat.xaeros.train;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.simibubi.create.compat.trainmap.TrainMapSync;
import com.simibubi.create.compat.trainmap.TrainMapSync.TrainMapSyncEntry;
import com.simibubi.create.compat.trainmap.TrainMapSyncClient;
import com.simibubi.create.content.trains.entity.Train;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import xaero.lib.client.graphics.XaeroRenderType;
import xaero.lib.client.graphics.shader.LibShaders;
import xaero.map.graphics.MapRenderHelper;

public class TrainIconRenderer {

    public void renderIcon(GuiGraphics guiGraphics, Train train) {
        PoseStack matrixStack = guiGraphics.pose();

        double time = AnimationTickHolder.getTicks();
        time += AnimationTickHolder.getPartialTicks();
        time -= TrainMapSyncClient.lastPacket;
        time /= TrainMapSync.lightPacketInterval;
        time = Mth.clamp(time, 0, 1);

        @Nullable TrainMapSyncEntry trainEntry = TrainMapSyncClient.currentData.get(train.id);

        Vec3 pos1, pos2;

        if (trainEntry == null) {
            pos1 = pos2 = new Vec3(0.0, 0.0, 0.0);
        }
        else {
            pos1 = trainEntry.getPosition(0, true, time);
            pos2 = trainEntry.getPosition(0, false, time);
        }

        Vec3 diff = pos2.subtract(pos1);

        int rotation = Mth.positiveModulo(Mth.floor(0.5 + (Math.atan2(diff.x, diff.z) * Mth.RAD_TO_DEG) / 22.5), 8);

        if (trainEntry != null && trainEntry.state == TrainMapSync.TrainState.DERAILED)
            rotation = Mth.positiveModulo((AnimationTickHolder.getTicks() / 8), 8);

        int trainColorIndex = train.mapColorIndex;
        int colorRow = trainColorIndex / 4;
        int colorCol = trainColorIndex % 4;

        AllGuiTextures sprite = AllGuiTextures.TRAINMAP_SPRITES;

        int sheetX = rotation * 16 + colorCol * 128;
        int sheetY = colorRow * 64;

        RenderSystem.setShaderTexture(0, sprite.location);
        RenderSystem.setShader(() -> LibShaders.POSITION_COLOR_TEX);

        BufferBuilder
                bufferbuilder =
                Tesselator.getInstance().begin(VertexFormat.Mode.QUADS, XaeroRenderType.POSITION_COLOR_TEX);

        MapRenderHelper.blitIntoExistingBuffer(matrixStack.last().pose(),
                bufferbuilder,
                -6.0F,
                -8.0F,
                sheetX,
                sheetY,
                16,
                16,
                16,
                16,
                1.0F,
                1.0F,
                1.0F,
                1.0F,
                sprite.getWidth(),
                sprite.getHeight());

        BufferUploader.drawWithShader(bufferbuilder.build());
    }

}

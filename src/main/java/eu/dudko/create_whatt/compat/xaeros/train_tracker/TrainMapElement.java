package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import com.simibubi.create.compat.trainmap.TrainMapSync;
import com.simibubi.create.compat.trainmap.TrainMapSync.TrainMapSyncEntry;
import com.simibubi.create.compat.trainmap.TrainMapSyncClient;
import com.simibubi.create.content.trains.entity.Train;
import net.createmod.catnip.animation.AnimationTickHolder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public class TrainMapElement {

    private final Train train;

    public TrainMapElement(Train train) {
        this.train = train;
    }

    public String getTrainNameString() {
        return train.name.getString();
    }

    public TrainMapSyncEntry getTrainSyncEntry() {
        return TrainMapSyncClient.currentData.get(train.id);
    }

    public UUID getTrainId() {
        return train.id;
    }

    public double getX() {
        return getPosition().x();
    }

    public double getY() {
        return getPosition().y();
    }

    public double getZ() {
        return getPosition().z();
    }

    public Vec3 getPosition() {
        double time = AnimationTickHolder.getTicks();
        time += AnimationTickHolder.getPartialTicks();
        time -= TrainMapSyncClient.lastPacket;
        time /= TrainMapSync.lightPacketInterval;
        time = Mth.clamp(time, 0, 1);

        return getTrainSyncEntry().getPosition(0, true, time);

    }

    public Train getTrain() {
        return train;
    }

    public List<ResourceKey<Level>> getDimensions() {
        return train.getPresentDimensions();
    }

    public boolean isInDimension(@Nullable ResourceKey<Level> dimension) {
        if (dimension == null) return false;
        return train.getPresentDimensions().contains(dimension);
    }
}

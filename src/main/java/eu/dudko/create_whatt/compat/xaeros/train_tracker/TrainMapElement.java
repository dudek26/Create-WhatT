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
import java.util.Optional;
import java.util.UUID;

public class TrainMapElement {

    private final Train train;

    public TrainMapElement(Train train) {
        this.train = train;
    }

    public String getTrainNameString() {
        return train.name.getString();
    }

    public Optional<TrainMapSyncEntry> getTrainSyncEntry() {
        return Optional.ofNullable(TrainMapSyncClient.currentData.get(train.id));
    }

    public UUID getTrainId() {
        return train.id;
    }

    public double getX(boolean fromFirstCarriage) {
        return getPosition(fromFirstCarriage).x();
    }

    public double getY(boolean fromFirstCarriage) {
        return getPosition(fromFirstCarriage).y();
    }

    public double getZ(boolean fromFirstCarriage) {
        return getPosition(fromFirstCarriage).z();
    }

    /**
     * @param fromFirstCarriage true will return the position of the first bogey in the first assembled carriage, false may return the position of the last bogey in the last carriage, if the train is driving backwards
     */
    public Vec3 getPosition(boolean fromFirstCarriage) {
        return getTrainSyncEntry().map(entry -> {
            double time = AnimationTickHolder.getTicks();
            time += AnimationTickHolder.getPartialTicks();
            time -= TrainMapSyncClient.lastPacket;
            time /= TrainMapSync.lightPacketInterval;
            time = Mth.clamp(time, 0, 1);

            if (fromFirstCarriage) {
                return entry.getPosition(0, true, time);
            }

            boolean backwards = entry.backwards;
            return entry.getPosition(backwards ? train.carriages.size() - 1 : 0, !backwards, time);

        }).orElse(new Vec3(0, 0, 0));
    }

    public Train getTrain() {
        return train;
    }

    public List<ResourceKey<Level>> getDimensions() {
        return getTrainSyncEntry().map(entry -> entry.dimensions).orElse(List.of());
    }

    public boolean isInDimension(@Nullable ResourceKey<Level> dimension) {
        if (dimension == null) return false;
        return getDimensions().contains(dimension);
    }
}

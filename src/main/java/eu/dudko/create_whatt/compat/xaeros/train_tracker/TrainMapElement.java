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

public record TrainMapElement(Train train) {

    static final List<TrainMapSync.TrainState>
            warningStates =
            List.of(TrainMapSync.TrainState.DERAILED,
                    TrainMapSync.TrainState.CONDUCTOR_MISSING,
                    TrainMapSync.TrainState.SCHEDULE_INTERRUPTED,
                    TrainMapSync.TrainState.NAVIGATION_FAILED);

    public String getTrainNameString() {
        return train.name.getString();
    }

    public Optional<TrainMapSyncEntry> getTrainSyncEntry() {
        return Optional.ofNullable(TrainMapSyncClient.currentData.get(train.id));
    }

    public UUID getTrainId() {
        return train.id;
    }

    /**
     * @param fromFirstCarriage true will return the position of the first bogey in the first assembled carriage, false may return the position of the last bogey in the last carriage, if the train is driving backwards
     */
    public double getX(boolean fromFirstCarriage) {
        return getPosition(fromFirstCarriage).x();
    }

    /**
     * @param fromFirstCarriage true will return the position of the first bogey in the first assembled carriage, false may return the position of the last bogey in the last carriage, if the train is driving backwards
     */
    public double getY(boolean fromFirstCarriage) {
        return getPosition(fromFirstCarriage).y();
    }

    /**
     * @param fromFirstCarriage true will return the position of the first bogey in the first assembled carriage, false may return the position of the last bogey in the last carriage, if the train is driving backwards
     */
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

        }).orElse(Vec3.ZERO);
    }

    public List<ResourceKey<Level>> getDimensions() {
        return getTrainSyncEntry().map(entry -> entry.dimensions).orElse(List.of());
    }

    public boolean isDrivingBackwards() {
        return getTrainSyncEntry().map(entry -> entry.backwards).orElse(false);
    }

    /**
     * @param fromFirstCarriage true will return the position of the first bogey in the first assembled carriage, false may return the position of the last bogey in the last carriage, if the train is driving backwards
     */
    @Nullable
    public ResourceKey<Level> getFirstDimension(boolean fromFirstCarriage) {
        if (getDimensions().isEmpty()) return null;
        return fromFirstCarriage || !isDrivingBackwards() ? getDimensions().getFirst() : getDimensions().getLast();
    }

    /**
     * @param fromFirstCarriage true will return the position of the first bogey in the first assembled carriage, false may return the position of the last bogey in the last carriage, if the train is driving backwards
     */
    public boolean isSameDimension(ResourceKey<Level> dimension, boolean fromFirstCarriage) {
        return getFirstDimension(fromFirstCarriage) == dimension;
    }

    public boolean hasWarning() {
        return this.getTrainSyncEntry().map(entry -> warningStates.contains(entry.state)).orElse(false);
    }

}

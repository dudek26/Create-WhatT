package eu.dudko.create_whatt.compat.xaeros.train_tracker;

import com.simibubi.create.content.trains.entity.Train;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import xaero.map.animation.SlowingAnimation;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TrainTrackerMapElement {
    private final Train train;
    private SlowingAnimation fadeAnim;

    public TrainTrackerMapElement(Train train) {
        this.train = train;
    }

    public String getTrainNameString() {
        return train.name.getString();
    }

    public UUID getTrainId() {
        return train.id;
    }

    public int getX(ResourceKey<Level> dimension) {
        return getPosition(dimension).map(Vec3i::getX).orElse(0);
    }

    public int getY(ResourceKey<Level> dimension) {
        return getPosition(dimension).map(Vec3i::getY).orElse(0);
    }

    public int getZ(ResourceKey<Level> dimension) {
        return getPosition(dimension).map(Vec3i::getZ).orElse(0);
    }

    public Optional<BlockPos> getPosition(ResourceKey<Level> dimension) {
        return train.getPositionInDimension(dimension);
    }

    public Train getTrain() {
        return train;
    }

    public List<ResourceKey<Level>> getDimensions() {
        return train.getPresentDimensions();
    }

    public SlowingAnimation getFadeAnim() {
        return fadeAnim;
    }

    public boolean isInDimension(@Nullable ResourceKey<Level> dimension) {
        if (dimension == null) return false;
        return train.getPresentDimensions().contains(dimension);
    }
}

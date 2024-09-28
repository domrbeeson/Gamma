package domrbeeson.gamma.player;

import domrbeeson.gamma.entity.Entity;
import org.jetbrains.annotations.NotNull;

public record EntityInRange(Entity<?> entity, double distanceToEntity) implements Comparable<EntityInRange> {
    @Override
    public int compareTo(@NotNull EntityInRange playerRange) {
        return Double.compare(distanceToEntity, playerRange.distanceToEntity);
    }
}

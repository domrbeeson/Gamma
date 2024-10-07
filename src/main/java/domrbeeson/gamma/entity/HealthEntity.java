package domrbeeson.gamma.entity;

import domrbeeson.gamma.entity.metadata.EntityMetadata;
import domrbeeson.gamma.world.World;

public abstract class HealthEntity<T extends EntityMetadata> extends Entity<T> {

    private final double maxHealth;

    private short health;

    public HealthEntity(EntityType type, World world, Pos pos, T metadata, CollisionBox collisionBox, short maxHealth) {
        super(type, world, pos, metadata, collisionBox);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public short getHealth() {
        return health;
    }

    public void setHealth(short health) {
        this.health = health;
    }

    @Override
    public void tick(long ticks) {
        super.tick(ticks);

        if (health <= 0) {
            // TODO die
        }
    }

}

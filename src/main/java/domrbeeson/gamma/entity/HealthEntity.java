package domrbeeson.gamma.entity;

import domrbeeson.gamma.entity.metadata.EntityMetadata;
import domrbeeson.gamma.world.World;

public abstract class HealthEntity<T extends EntityMetadata> extends Entity<T> {

    private final double maxHealth;

    private double health;

    public HealthEntity(EntityType type, World world, Pos pos, T metadata, CollisionBox collisionBox, double maxHealth) {
        super(type, world, pos, metadata, collisionBox);
        this.maxHealth = maxHealth;
        this.health = maxHealth;
    }

    public double getMaxHealth() {
        return maxHealth;
    }

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
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

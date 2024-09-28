package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.mobs.PigEntity;

public class SpawnerTileEntity extends TileEntity {

    private Class<? extends LivingEntity<?>> entity;
    private short delayTicks = 20;

    public SpawnerTileEntity(int x, int y, int z) {
        this(x, y, z, PigEntity.class);
    }

    public SpawnerTileEntity(int x, int y, int z, Class<? extends LivingEntity<?>> entity) {
        super(x, y, z);
        this.entity = entity;
    }

    @Override
    public void tick(long ticks) {
        // TODO check for players in range

        // TODO when entity spawns, set delay to 200 + random.nextInt(600)
    }

    public Class<? extends LivingEntity<?>> getEntity() {
        return entity;
    }

    public void setEntity(Class<? extends LivingEntity<?>> entity) {
        this.entity = entity;
        // TODO update clients
    }

    public short getDelayTicks() {
        return delayTicks;
    }

}

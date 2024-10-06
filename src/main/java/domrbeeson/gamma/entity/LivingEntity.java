package domrbeeson.gamma.entity;

import domrbeeson.gamma.entity.metadata.LivingEntityMetadata;
import domrbeeson.gamma.entity.mobs.*;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.network.packet.out.LivingEntitySpawnPacketOut;
import domrbeeson.gamma.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public abstract class LivingEntity<T extends LivingEntityMetadata> extends HealthEntity<T> {

    private static final Map<Class<? extends LivingEntity<?>>, String> ENTITY_TO_NAME = new HashMap<>() {{
        put(CreeperEntity.class, CreeperEntity.NAME);
        put(SkeletonEntity.class, SkeletonEntity.NAME);
        put(SpiderEntity.class, SpiderEntity.NAME);
        put(GiantZombieEntity.class, GiantZombieEntity.NAME);
        put(ZombieEntity.class, ZombieEntity.NAME);
        put(SlimeEntity.class, SlimeEntity.NAME);
        put(GhastEntity.class, GhastEntity.NAME);
        put(ZombiePigmanEntity.class, ZombiePigmanEntity.NAME);
        put(PigEntity.class, PigEntity.NAME);
        put(SheepEntity.class, SheepEntity.NAME);
        put(CowEntity.class, CowEntity.NAME);
        put(ChickenEntity.class, ChickenEntity.NAME);
        put(SquidEntity.class, SquidEntity.NAME);
        put(WolfEntity.class, WolfEntity.NAME);
    }};
    private static final Map<String, Class<? extends LivingEntity<?>>> NAME_TO_ENTITY = new HashMap<>();

    static {
        ENTITY_TO_NAME.forEach((entity, name) -> NAME_TO_ENTITY.put(name, entity));
    }

    public LivingEntity(EntityType type, World world, Pos pos, T metadata, CollisionBox collisionBox, short maxHealth) {
        super(type, world, pos, metadata, collisionBox, maxHealth);
    }

    public static String getNameFromEntity(Class<? extends LivingEntity<?>> entity) {
        return ENTITY_TO_NAME.get(entity);
    }

    public static @Nullable Class<? extends LivingEntity<?>> getEntityFromName(String name) {
        return NAME_TO_ENTITY.get(name);
    }

    @Override
    public PacketOut getSpawnPacket() {
        return new LivingEntitySpawnPacketOut(this);
    }

}

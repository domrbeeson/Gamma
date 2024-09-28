package domrbeeson.gamma.nbt.world;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.ItemEntity;
import domrbeeson.gamma.entity.LivingEntity;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.entity.mobs.*;
import domrbeeson.gamma.entity.object.*;
import domrbeeson.gamma.nbt.NBTCompoundCreator;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.*;
import domrbeeson.gamma.nbt.world.entity.*;
import domrbeeson.gamma.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class NBTEntity implements NBTCompoundCreator {

	private static final Map<Class<? extends Entity<?>>, Function<Entity<?>, NBTEntity>> ENTITY_TO_NBT = new HashMap<>() {{
		put(CreeperEntity.class, NBTCreeper::new);
		put(SkeletonEntity.class, NBTSkeleton::new);
		put(SpiderEntity.class, NBTSpider::new);
		put(GiantZombieEntity.class, NBTGiant::new);
		put(ZombieEntity.class, NBTZombie::new);
		put(SlimeEntity.class, NBTSlime::new);
		put(GhastEntity.class, NBTGhast::new);
		put(ZombiePigmanEntity.class, NBTZombiePigman::new);
		put(PigEntity.class, NBTPig::new);
		put(SheepEntity.class, NBTSheep::new);
		put(CowEntity.class, NBTCow::new);
		put(ChickenEntity.class, NBTChicken::new);
		put(SquidEntity.class, NBTSquid::new);
		put(WolfEntity.class, NBTWolf::new);

		put(ItemEntity.class, NBTItem::new);
		put(PaintingEntity.class, NBTPainting::new);
		put(BoatEntity.class, NBTBoat::new);
		put(MinecartEntity.class, NBTMinecart::new);
		put(ChestMinecartEntity.class, NBTChestMinecart::new);
		put(FurnaceMinecartEntity.class, NBTFurnaceMinecart::new);
		put(PrimedTntEntity.class, NBTPrimedTnt::new);
		put(ArrowEntity.class, NBTArrow::new);
		put(SnowballEntity.class, NBTSnowball::new);
		put(FallingBlockEntity.class, NBTFallingBlock::new);

		// Fireballs, Fishing floats and Eggs are not saved to world file
	}};
	private static final Map<String, Function<NBTCompound, NBTEntity>> NAME_TO_NBT = new HashMap<>() {{
		put(CreeperEntity.NAME, NBTCreeper::new);
		put(SkeletonEntity.NAME, NBTSkeleton::new);
		put(SpiderEntity.NAME, NBTSpider::new);
		put(GiantZombieEntity.NAME, NBTGiant::new);
		put(ZombieEntity.NAME, NBTZombie::new);
		put(SlimeEntity.NAME, NBTSlime::new);
		put(GhastEntity.NAME, NBTGhast::new);
		put(ZombiePigmanEntity.NAME, NBTZombiePigman::new);
		put(PigEntity.NAME, NBTPig::new);
		put(SheepEntity.NAME, NBTSheep::new);
		put(CowEntity.NAME, NBTCow::new);
		put(ChickenEntity.NAME, NBTChicken::new);
		put(SquidEntity.NAME, NBTSquid::new);
		put(WolfEntity.NAME, NBTWolf::new);

		put(ItemEntity.NAME, NBTItem::new);
		put(PaintingEntity.NAME, NBTPainting::new);
		put(BoatEntity.NAME, NBTBoat::new);
		put(MinecartEntity.NAME, NBTMinecart::new); // TODO distinguish between minecart/chest/furnace
		put(PrimedTntEntity.NAME, NBTPrimedTnt::new);
		put(ArrowEntity.NAME, NBTArrow::new);
		put(SnowballEntity.NAME, NBTSnowball::new);
		put(FallingBlockEntity.NAME, NBTFallingBlock::new);
	}};
	private static final Map<Class<? extends Entity<?>>, String> ENTITY_TO_NAME = new HashMap<>() {{
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

		put(ItemEntity.class, ItemEntity.NAME);
		put(PaintingEntity.class, PaintingEntity.NAME);
		put(BoatEntity.class, BoatEntity.NAME);
		put(MinecartEntity.class, MinecartEntity.NAME);
		put(PrimedTntEntity.class, PrimedTntEntity.NAME);
		put(ArrowEntity.class, ArrowEntity.NAME);
		put(SnowballEntity.class, SnowballEntity.NAME);
		put(FallingBlockEntity.class, FallingBlockEntity.NAME);
	}};

	private final String name;
	private final Pos pos;
	private final double xVelocity, yVelocity, zVelocity;
	private final float fallDistance;
	private final short fireTicks, airTicks;
	private final boolean onGround;

	public NBTEntity(NBTCompound compound) {
		name = compound.getString("id").getValue();

		NBTList position = compound.getList("Pos");
		double x = position.getDouble(0).getValue();
		double y = position.getDouble(1).getValue();
		double z = position.getDouble(2).getValue();
		NBTList rotation = compound.getList("Rotation");
		float yaw = rotation.getFloat(0).getValue();
		float pitch = rotation.getFloat(1).getValue();
		pos = new Pos(x, y, z, yaw, pitch);

		NBTList motion = compound.getList("Motion");
		xVelocity = motion.getDouble(0).getValue();
		yVelocity = motion.getDouble(1).getValue();
		zVelocity = motion.getDouble(2).getValue();

		fallDistance = compound.getFloat("FallDistance").getValue();
		fireTicks = compound.getShort("Fire").getValue();
		airTicks = compound.getShort("Air").getValue();
		onGround = compound.getByte("OnGround").getValue() == 1;
	}

	public NBTEntity(String name, Entity<?> entity) {
		this.name = name;
		pos = entity.getPos();
		// TODO entity velocity
		xVelocity = 0;
		yVelocity = 0;
		zVelocity = 0;
		// TODO load metadata
		fallDistance = 0;
		fireTicks = 0;
		airTicks = 0;
		onGround = false;
	}

	public static NBTEntity createNBTEntity(Entity<?> entity) {
		return ENTITY_TO_NBT.get(entity.getClass()).apply(entity);
	}

	public static NBTEntity createNBTEntity(NBTCompound compound) {
		String name = compound.getString("id").getValue();
		return NAME_TO_NBT.get(name).apply(compound);
	}

	public static String getEntityName(Class<? extends LivingEntity<?>> entity) {
		return ENTITY_TO_NAME.get(entity);
	}

	@Override
	public Map<String, NBTTag> createCompoundTags() {
		Map<String, NBTTag> tags = new HashMap<>();

		tags.put("id", new NBTString(name));

		List<NBTTag> position = new ArrayList<>();
		position.add(new NBTDouble(pos.x()));
		position.add(new NBTDouble(pos.y()));
		position.add(new NBTDouble(pos.z()));
		tags.put("Pos", new NBTList(position));

		List<NBTTag> motion = new ArrayList<>();
		motion.add(new NBTDouble(xVelocity));
		motion.add(new NBTDouble(yVelocity));
		motion.add(new NBTDouble(zVelocity));
		tags.put("Motion", new NBTList(motion));

		List<NBTTag> rotation = new ArrayList<>();
		rotation.add(new NBTFloat(pos.yaw()));
		rotation.add(new NBTFloat(pos.pitch()));
		tags.put("Rotation", new NBTList(rotation));

		tags.put("FallDistance", new NBTFloat(fallDistance));
		tags.put("Fire", new NBTShort(fireTicks));
		tags.put("Air", new NBTShort(airTicks));
		tags.put("OnGround", new NBTByte(onGround));

		return tags;
	}

	public abstract Entity<?> createEntity(World world);

	public Pos getPos() {
		return pos;
	}

	public double getXVelocity() {
		return xVelocity;
	}

	public double getYVelocity() {
		return yVelocity;
	}

	public double getZVelocity() {
		return zVelocity;
	}

	public float getFallDistance() {
		return fallDistance;
	}

	public int getFireTicks() {
		return fireTicks;
	}

	public int getAirTicks() {
		return airTicks;
	}

	public boolean isOnGround() {
		return onGround;
	}

}

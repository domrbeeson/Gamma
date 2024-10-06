package domrbeeson.gamma.nbt.world;

import domrbeeson.gamma.Saveable;
import domrbeeson.gamma.crafting.RecipeManager;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.inventory.InventoryType;
import domrbeeson.gamma.inventory.PlayerInventory;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.nbt.NBTCompoundCreator;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.*;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Dimension;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBTPlayer implements Saveable, NBTCompoundCreator {

	private final File playerDatFile;
	private final short air, attackTime, deathTime, fireTicks, health, hurtTime, sleepTimer;
	private final Dimension dimension;
	private final float fallDistance;
	private final boolean onGround, sleeping;
	private final PlayerInventory inv;
	private final double[] motion;
	private final Pos pos;

	public NBTPlayer(File playerDatFile, NBTCompound compound, String username, RecipeManager recipeManager) {
		this.playerDatFile = playerDatFile;
		air = compound.getShort("Air").getValue();
		attackTime = compound.getShort("AttackTime").getValue();
		deathTime = compound.getShort("DeathTime").getValue();
		dimension = Dimension.getById(compound.getInt("Dimension").getValue());
		fallDistance = compound.getFloat("FallDistance").getValue();
		fireTicks = compound.getShort("Fire").getValue();
		health = compound.getShort("Health").getValue();
		hurtTime = compound.getShort("HurtTime").getValue();
		Item[] invItems = new Item[InventoryType.PLAYER.slots];
		compound.getList("Inventory").getValue().forEach(tag -> {
			NBTCompound itemCompound = (NBTCompound) tag;
			Item item = Material.get(
					itemCompound.getShort("id").getValue(),
					itemCompound.getShort("Damage").getValue()
			).getItem(itemCompound.getByte("Count").getValue());
			invItems[itemCompound.getByte("Slot").getValue()] = item;
		});
		inv = new PlayerInventory(username, recipeManager, invItems);
		motion = new double[3];
		int i = 0;
		for (NBTTag tag : compound.getList("Motion").getValue()) {
			motion[i++] = ((NBTDouble) tag).getValue();
		}
		onGround = compound.getByte("OnGround").getValue() == 1;
		List<NBTTag> pos = compound.getList("Pos").getValue();
		List<NBTTag> rotation = compound.getList("Rotation").getValue();
		this.pos = new Pos(((NBTDouble) pos.get(0)).getValue(), ((NBTDouble) pos.get(1)).getValue(), ((NBTDouble) pos.get(2)).getValue(), ((NBTFloat) rotation.get(0)).getValue(), ((NBTFloat) rotation.get(1)).getValue());
		sleepTimer = compound.getShort("SleepTimer").getValue();
		sleeping = compound.getByte("Sleeping").getValue() == 1;
	}

	public NBTPlayer(File playerDatFile, Player player) {
		this.playerDatFile = playerDatFile;
		// TODO most of the fields need real values
		air = 0;
		attackTime = 0;
		deathTime = 0;
		dimension = player.getWorld().getFormat().getDimension();
		fallDistance = 0;
		fireTicks = 0;
		health = player.getHealth();
		hurtTime = 0;
		inv = player.getInventory();
		motion = new double[3];
		onGround = player.isOnGround();
		pos = player.getPos();
		sleepTimer = 0;
		sleeping = false;
	}

	public short getAir() {
		return air;
	}

	public short getAttackTime() {
		return attackTime;
	}

	public short getDeathTime() {
		return deathTime;
	}

	public Dimension getDimension() {
		return dimension;
	}

	public float getFallDistance() {
		return fallDistance;
	}

	public short getFireTicks() {
		return fireTicks;
	}

	public short getHealth() {
		return health;
	}

	public short getHurtTime() {
		return hurtTime;
	}

	public PlayerInventory getInventory() {
		return inv;
	}

	public double[] getMotion() {
		return motion;
	}

	public Pos getPos() {
		return pos;
	}

	public boolean onGround() {
		return onGround;
	}

	public short getSleepTimer() {
		return sleepTimer;
	}

	public boolean isSleeping() {
		return sleeping;
	}

	@Override
	public Map<String, NBTTag> createCompoundTags() {
		return new HashMap<>() {{
			put("Air", new NBTShort(air));
			put("AttackTime", new NBTShort(attackTime));
			put("DeathTime", new NBTShort(deathTime));
			put("Dimension", new NBTInt(dimension.id));
			put("FallDistance", new NBTFloat(fallDistance));
			put("Fire", new NBTShort(fireTicks));
			put("Health", new NBTShort(health));
			put("HurtTime", new NBTShort(hurtTime));
			List<NBTTag> invItems = new ArrayList<>();
			Item[] slots = inv.getSlots();
            for (byte slot = 0; slot < slots.length; slot++) {
				Item item = slots[slot];
                if (item == null) {
                    continue;
                }
				final byte s = slot;
                invItems.add(new NBTCompound(new HashMap<>() {{
					put("id", new NBTShort(item.id()));
					put("Damage", new NBTShort(item.metadata()));
					put("Count", new NBTByte(item.amount()));
					put("Slot", new NBTByte(s));
                }}));
            }
			put("Inventory", new NBTList(invItems));
			put("Motion", new NBTList(new ArrayList<>() {{
				add(new NBTDouble(motion[0]));
				add(new NBTDouble(motion[1]));
				add(new NBTDouble(motion[2]));
			}}));
			put("OnGround", new NBTByte(onGround));
			put("Pos", new NBTList(new ArrayList<>() {{
				add(new NBTDouble(pos.x()));
				add(new NBTDouble(pos.y()));
				add(new NBTDouble(pos.z()));
			}}));
			put("Rotation", new NBTList(new ArrayList<>() {{
				add(new NBTFloat(pos.yaw()));
				add(new NBTFloat(pos.pitch()));
			}}));
			put("SleepTimer", new NBTShort(sleepTimer));
			put("Sleeping", new NBTByte(sleeping));
		}};
	}

	@Override
	public void save() {
		new NBTCompound(createCompoundTags()).saveToFile(playerDatFile);
	}

}

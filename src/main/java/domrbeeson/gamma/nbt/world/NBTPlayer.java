package domrbeeson.gamma.nbt.world;

import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.world.Dimension;
import domrbeeson.gamma.world.World;

public class NBTPlayer extends NBTEntity {

	private Dimension dimension;
	// TODO inventory

	public NBTPlayer(NBTCompound compound) {
		super(compound);

		int dimensionValue = compound.getInt("Dimension").getValue();
		dimension = Dimension.getById((byte) dimensionValue);
	}

	@Override
	public Entity<?> createEntity(World world) {
		throw new UnsupportedOperationException("Cannot create Player from NBT");
	}

}

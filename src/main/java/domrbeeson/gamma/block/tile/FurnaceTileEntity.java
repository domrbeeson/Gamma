package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.inventory.FurnaceInventory;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

public class FurnaceTileEntity extends InventoryTileEntity<FurnaceInventory> {

    private boolean burning = false;

    public FurnaceTileEntity(Chunk chunk, int x, int y, int z) {
        super(chunk, x, y, z, new FurnaceInventory());
    }

    @Override
    public void tick(long ticks) {
        super.tick(ticks);

        if (!burning && getInventory().isBurning()) {
            burning = true;
            getChunk().setBlock(getX(), getY(), getZ(), Material.FURNACE_BURNING);
        } else if (burning && !getInventory().isBurning()) {
            burning = false;
            getChunk().setBlock(getX(), getY(), getZ(), Material.FURNACE);
        }
    }

}

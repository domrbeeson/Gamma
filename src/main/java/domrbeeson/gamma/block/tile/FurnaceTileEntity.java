package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.inventory.FurnaceInventory;
import domrbeeson.gamma.world.Chunk;

public class FurnaceTileEntity extends InventoryTileEntity<FurnaceInventory> {

    public FurnaceTileEntity(Chunk chunk, int x, int y, int z) {
        super(chunk, x, y, z, new FurnaceInventory());
    }

    @Override
    public void tick(long ticks) {
        super.tick(ticks);

        // TODO if is burning and blockid is not burning furnace, update block to burning furnace
    }

}

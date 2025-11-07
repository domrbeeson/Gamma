package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.world.Chunk;

public abstract class TileEntityBlockHandler<T extends TileEntity> implements BlockHandler {

    private final Class<T> clazz;

    public TileEntityBlockHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onBreak(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata) {
        TileEntity tile = getTileEntity(chunk, x, y, z);
        if (tile == null) {
            return;
        }
        chunk.removeTileEntity(tile);
    }

    public T getTileEntity(Chunk chunk, int x, int y, int z) {
        TileEntity tile = chunk.getWorld().getTileEntity(x, y, z);
        if (tile != null && tile.getClass() == clazz) {
            return (T) tile;
        }
        return null;
    }

}

package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.block.tile.TileEntity;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

public abstract class TileEntityBlockHandler<T extends TileEntity> extends BlockHandler {

    private final Class<T> clazz;

    public TileEntityBlockHandler(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public void onBreak(MinecraftServer server, Block block, Player player) {
        TileEntity tile = getTileEntity(block.chunk(), block.x(), block.y(), block.z());
        if (tile == null) {
            return;
        }
        block.chunk().removeTileEntity(tile);
    }

    public T getTileEntity(Chunk chunk, int x, int y, int z) {
        TileEntity tile = chunk.getTileEntity(x, y, z);
        if (tile != null && tile.getClass() == clazz) {
            return (T) tile;
        }
        return null;
    }

}

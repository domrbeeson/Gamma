package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.world.Chunk;

public class JukeboxTileEntity extends TileEntity {

    private short discItemId;

    public JukeboxTileEntity(Chunk chunk, int x, int y, int z) {
        this(chunk, x, y, z, (short) 0);
    }

    public JukeboxTileEntity(Chunk chunk, int x, int y, int z, short discItemId) {
        super(chunk, x, y, z);
        this.discItemId = discItemId;
    }

    @Override
    public void tick(long ticks) {

    }

    public short getDiscItemId() {
        return discItemId;
    }

    public boolean hasDisc() {
        return discItemId > 0;
    }

    public void setDisc(short discItemId) {
        this.discItemId = discItemId;
        // TODO update music on client
    }

}

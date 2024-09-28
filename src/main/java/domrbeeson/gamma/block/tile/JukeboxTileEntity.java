package domrbeeson.gamma.block.tile;

public class JukeboxTileEntity extends TileEntity {

    private short discItemId;

    public JukeboxTileEntity(int x, int y, int z) {
        this(x, y, z, (short) 0);
    }

    public JukeboxTileEntity(int x, int y, int z, short discItemId) {
        super(x, y, z);
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

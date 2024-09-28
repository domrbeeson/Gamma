package domrbeeson.gamma.block.tile;

public class NoteBlockTileEntity extends TileEntity {

    private byte note;

    public NoteBlockTileEntity(int x, int y, int z) {
        this(x, y, z, (byte) 0);
    }

    public NoteBlockTileEntity(int x, int y, int z, byte note) {
        super(x, y, z);
        this.note = note;
    }

    @Override
    public void tick(long ticks) {

    }

    public byte getNote() {
        return note;
    }

    public void setNote(byte note) {
        this.note = sanitiseNote(note);
    }

    private byte sanitiseNote(byte note) {
        if (note < 0) {
            note = 0;
        } else if (note > 24) {
            note = 24;
        }
        return note;
    }

}

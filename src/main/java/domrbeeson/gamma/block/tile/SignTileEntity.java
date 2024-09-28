package domrbeeson.gamma.block.tile;

public class SignTileEntity extends TileEntity {

    public static final int MAX_LINE_LENGTH = 15;

    private final String[] lines;

    public SignTileEntity(int x, int y, int z) {
        this(x, y, z, new String[] { "", "", "", "" });
    }

    public SignTileEntity(int x, int y, int z, String[] lines) {
        super(x, y, z);
        this.lines = lines;
    }

    @Override
    public void tick(long ticks) {

    }

    public String getLine(int line) {
        if (!isLineValid(line)) {
            return null;
        }
        return lines[line];
    }

    public String[] getLines() {
        return lines;
    }

    public void setLine(int line, String text) {
        if (!isLineValid(line)) {
            return;
        }
        if (text.length() > MAX_LINE_LENGTH) {
            return;
        }
        lines[line] = text;
        // TODO update on clients
    }

    private boolean isLineValid(int line) {
        return line >= 0 && line < lines.length;
    }

}

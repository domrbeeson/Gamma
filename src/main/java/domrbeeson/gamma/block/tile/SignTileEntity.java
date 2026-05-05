package domrbeeson.gamma.block.tile;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.out.SignUpdatePacketOut;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import org.jetbrains.annotations.Nullable;

public class SignTileEntity extends TileEntity {

    public static final int MAX_LINE_LENGTH = 15;

    private final String[] lines;

    private boolean sendUpdate = true;

    public SignTileEntity(Chunk chunk, int x, int y, int z) {
        this(chunk, x, y, z, new String[] { "", "", "", "" });
    }

    public SignTileEntity(Chunk chunk, int x, int y, int z, String[] lines) {
        super(chunk, x, y, z);
        this.lines = lines;
    }

    @Override
    public void tick(long ticks) {
        if (sendUpdate) {
            sendUpdate = false;
            SignUpdatePacketOut packet = new SignUpdatePacketOut(this);
            for (Player viewer : getChunk().getViewers()) {
                viewer.sendPacket(packet);
            }
        }
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

    public boolean setLines(String[] lines) {
        return setLines(lines, null);
    }

    public boolean setLines(String[] lines, @Nullable Player player) {
        if (player == null || !player.isEditingSign(new Pos(getX(), getY(), getZ()))) {
            return false;
        }
        if (lines.length > this.lines.length) {
            return false;
        }

        System.arraycopy(lines, 0, this.lines, 0, lines.length);

        sendUpdate = true;
        return true;
    }

    public void setLine(int line, String text) {
        if (!isLineValid(line)) {
            return;
        }
        if (!isTextValid(text)) {
            return;
        }
        lines[line] = text;
        sendUpdate = true;
    }

    private boolean isLineValid(int line) {
        return line >= 0 && line < lines.length;
    }

    private boolean isTextValid(String text) {
        return text.length() <= MAX_LINE_LENGTH;
    }

}

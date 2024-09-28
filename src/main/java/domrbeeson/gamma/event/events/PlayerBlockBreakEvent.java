package domrbeeson.gamma.event.events;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

public class PlayerBlockBreakEvent extends BlockBreakEvent {

    private final Player player;
    private final short toolId;

    public PlayerBlockBreakEvent(MinecraftServer server, Player player, Chunk chunk, int x, int y, int z, byte currentId, byte currentMetadata, boolean tick, short toolId) {
        super(server, chunk, x, y, z, currentId, currentMetadata, tick);
        this.player = player;
        this.toolId = toolId;
    }

    public Player getPlayer() {
        return player;
    }

    public short getTool() {
        return toolId;
    }

}

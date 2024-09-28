package domrbeeson.gamma.event.events;

import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;

public class PlayerBlockPlaceEvent extends BlockChangeEvent {

    private final Player player;

    public PlayerBlockPlaceEvent(Player player, Chunk chunk, int x, byte y, int z, byte currentId, byte currentMetadata, byte newId, byte newMetadata, boolean tick) {
        super(chunk, x, y, z, currentId, currentMetadata, newId, newMetadata, tick);
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

}

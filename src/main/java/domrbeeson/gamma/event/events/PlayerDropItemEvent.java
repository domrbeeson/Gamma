package domrbeeson.gamma.event.events;

import domrbeeson.gamma.player.Player;

public class PlayerDropItemEvent extends PlayerWorldEvent {

    private short id;
    private byte metadata;

    public PlayerDropItemEvent(Player player, short id, byte metadata) {
        super(player);
        this.id = id;
        this.metadata = metadata;
    }

    public short getId() {
        return id;
    }

    public byte getMetadata() {
        return metadata;
    }

}

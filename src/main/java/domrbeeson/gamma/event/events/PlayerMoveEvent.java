package domrbeeson.gamma.event.events;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.player.Player;

public class PlayerMoveEvent extends PlayerWorldEvent {

    private final Pos currentPos, newPos;
    private final boolean posChanged, lookChanged;

    public PlayerMoveEvent(Player player, Pos newPos) {
        super(player);
        this.newPos = newPos;
        this.currentPos = player.getPos();
        posChanged = newPos.x() != currentPos.x() || newPos.y() != currentPos.y() || newPos.z() != currentPos.z();
        lookChanged = newPos.yaw() != currentPos.yaw() || newPos.pitch() != currentPos.pitch();
    }

    public Pos getNewPos() {
        return newPos;
    }

    public Pos getCurrentPos() {
        return currentPos;
    }

    public boolean hasPositionChanged() {
        return posChanged;
    }

    public boolean hasLookChanged() {
        return lookChanged;
    }

}

package domrbeeson.gamma.event.events;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.event.Event;
import domrbeeson.gamma.player.Player;

public class PlayerCommandEvent extends CancellableEvent implements Event.GlobalEvent {

    private final Player player;
    private final Command command;
    private final String[] args;

    public PlayerCommandEvent(Player player, Command command, String... args) {
        this.player = player;
        this.command = command;
        this.args = args;
    }

    public Player getPlayer() {
        return player;
    }

    public Command getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

}
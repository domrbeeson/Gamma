package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerManager;

import java.util.Collection;
import java.util.stream.Collectors;

public class ListCommand implements Command {

    private static final String[] ALIASES = new String[] {
            "players"
    };

    private final PlayerManager playerManager;

    public ListCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public String getName() {
        return "list";
    }

    @Override
    public String[] getAliases() {
        return ALIASES;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        Collection<Player> players = playerManager.getPlayers();
        sender.sendMessage("Players online: " + playerManager.getPlayersOnline());
        String usernames = players.stream().map(Player::getUsername).collect(Collectors.joining(", "));
        sender.sendMessage(usernames);
    }
}

package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerManager;

public record ClearCommand(PlayerManager playerManager) implements Command {

    @Override
    public String getName() {
        return "clear";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0 && !sender.isPlayer()) {
            sender.sendMessage("/clear <player>");
            return;
        }

        Player player = args.length > 0 ? playerManager.get(args[0]) : (Player) sender;
        if (player == null) {
            sender.sendMessage("Player '" + args[0] + "' not found.");
            return;
        }

        sender.sendMessage("Cleared inventory of player '" + player.getUsername() + "'.");
        player.getInventory().clear();
    }
}

package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerManager;

public class KickCommand implements Command {

    private final PlayerManager playerManager;

    public KickCommand(PlayerManager playerManager) {
        this.playerManager = playerManager;
    }

    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return;
        }

        String username = args[0];
        String[] reasonParts = new String[args.length - 1];
        System.arraycopy(args, 1, reasonParts, 0, args.length - 1);
        String reason;
        if (reasonParts.length > 0) {
            reason = String.join(" ", reasonParts);
        } else {
            reason = "Kicked from server";
        }

        Player player = playerManager.get(username);
        if (player == null) {
            sender.sendMessage("'" + username + "' not found!");
            return;
        }

        sender.sendMessage("Kicked '" + username + "' for: " + reason);
        player.kick(reason);
    }

}

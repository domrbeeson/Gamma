package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.player.Player;

public class TeleportPosCommand implements Command {

    @Override
    public String getName() {
        return "tppos";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!sender.isPlayer()) {
            // TODO console should support teleporting a player to another player
            sender.sendMessage("Console cannot teleport");
            return;
        }

        if (args.length < 3) {
            sender.sendMessage("/tppos <x> <y> <z>");
            return;
        }

        double x, y, z;
        try {
            x = Double.parseDouble(args[0]);
            y = Double.parseDouble(args[1]);
            z = Double.parseDouble(args[2]);
        } catch (NumberFormatException e) {
            sender.sendMessage("/tppos <x> <y> <z>");
            return;
        }

        Player player = (Player) sender;
        player.sendMessage("Teleporting to [X: " + x + ", Y: " + y + ", Z: " + z + "]");
        Pos oldPos = player.getPos();
        player.teleport(player.getWorld(), new Pos(x, y, z, oldPos.yaw(), oldPos.pitch()));
    }

}

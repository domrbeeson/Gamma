package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.player.Player;

public class HoldingCommand implements Command {
    @Override
    public String getName() {
        return "holding";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Console cannot hold items");
            return;
        }

        Item heldItem = player.getInventory().getHeldItem();
        player.sendMessage(heldItem.amount() + "x " + heldItem.id() + ":" + heldItem.metadata() + " [" + heldItem.getMaterial().name() + "]");
    }
}

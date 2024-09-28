package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerManager;

public record ItemCommand(PlayerManager playerManager) implements Command {

    private static final String[] ALIASES = new String[] {
            "i",
            "give"
    };

    @Override
    public String getName() {
        return "item";
    }

    @Override
    public String[] getAliases() {
        return ALIASES;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length < 2) {
            sender.sendMessage("/give <player> <item> [amount]");
            return;
        }

        // TODO support metadata

        String username = args[0];
        Player giveToPlayer = playerManager.get(username);
        if (giveToPlayer == null) {
            sender.sendMessage("Player '" + username + "' not found.");
            return;
        }

        Material material = null;
        String itemId = args[1].toUpperCase();
        try {
            material = Material.get(Short.parseShort(itemId), (short) 0);
        } catch (Exception _) {

        }
        if (material == null) {
            sender.sendMessage("Item ID '" + itemId + "' not found.");
            return;
        }

        int amount = 1;
        if (args.length > 2) {
            String amountArg = args[2];
            try {
                amount = Integer.parseInt(amountArg);
            } catch (NumberFormatException e) {
                sender.sendMessage("'" + amountArg + "' is not a valid whole number.");
                return;
            }
            if (amount < 1) {
                amount = 1;
            }
        }

        sender.sendMessage("Giving '" + username + "' " + amount + "x " + material.name());
        giveToPlayer.getInventory().addItem(material, amount);
    }
}

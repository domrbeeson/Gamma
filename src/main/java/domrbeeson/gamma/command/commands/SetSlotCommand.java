package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.inventory.InventoryType;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.network.packet.out.WindowSlotPacketOut;
import domrbeeson.gamma.player.Player;

public class SetSlotCommand implements Command {
    @Override
    public String getName() {
        return "setslot";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("must be player");
            return;
        }

        if (args.length < 2) {
            sender.sendMessage("no");
            return;
        }

        int slot;
        try {
            slot = Integer.parseInt(args[0]);
        } catch (Exception e) {
            sender.sendMessage("'" + args[0] + "' is not a slot");
            return;
        }

        short itemId;
        try {
            itemId = Short.parseShort(args[1]);
        } catch (Exception e) {
            sender.sendMessage("'" + args[1] + "' is not an item ID");
            return;
        }

        Item item = new Item(itemId);
        player.getInventory().setSlot(slot, item);
        sender.sendMessage("set inv slot " + slot + " to " + item.getMaterial().name());
    }
}

package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.item.Material;

public class MaterialCommand implements Command {
    @Override
    public String getName() {
        return "material";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("/material <block id> [meta]");
            return;
        }

        short id;
        try {
            id = Short.parseShort(args[0]);
        } catch (Exception e) {
            sender.sendMessage("'" + args[0] + "' is an invalid block ID!");
            return;
        }

        short meta = 0;
        if (args.length >= 2) {
            try {
                meta = Short.parseShort(args[1]);
            } catch (Exception e) {
                sender.sendMessage("'" + args[1] + "' is invalid metadata!");
                return;
            }
        }

        sender.sendMessage(id + " = " + Material.get(id, (short) 0));
        sender.sendMessage(id + ":" + meta + " = " + Material.get(id, meta).name());
    }
}

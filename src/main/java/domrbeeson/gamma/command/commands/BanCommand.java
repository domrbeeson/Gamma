package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;

public class BanCommand implements Command {

    @Override
    public String getName() {
        return "ban";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            return;
        }

        String username = args[0];
        // TODO ban
        sender.sendMessage("TODO ban " + username);
    }
}

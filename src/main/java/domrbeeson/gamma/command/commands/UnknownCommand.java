package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;

public class UnknownCommand implements Command {

    @Override
    public String getName() {
        return "help";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        sender.sendMessage("Unknown command, TODO show help here");
    }
}

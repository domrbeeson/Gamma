package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;

public class GcCommand implements Command {
    @Override
    public String getName() {
        return "gc";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        sender.sendMessage("Running garbage collector");
        System.gc();
    }
}

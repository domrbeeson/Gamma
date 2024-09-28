package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;

public class MessageCommand implements Command {

    private static final String[] ALIASES = new String[] {
            "msg",
            "pm",
            "dm",
            "whisper",
            "tell"
    };

    @Override
    public String getName() {
        return "message";
    }

    @Override
    public String[] getAliases() {
        return ALIASES;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        sender.sendMessage("TODO");
    }
}

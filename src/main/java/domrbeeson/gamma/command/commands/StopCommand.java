package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;

public class StopCommand implements Command {

    private final MinecraftServer server;

    public StopCommand(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public String getName() {
        return "stop";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        server.stop();
    }
    
}

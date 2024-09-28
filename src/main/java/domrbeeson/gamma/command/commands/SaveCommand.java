package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;

public class SaveCommand implements Command {

    private final MinecraftServer server;

    public SaveCommand(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public String getName() {
        return "save";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        server.getWorldManager().getWorlds().forEach(world -> {
            sender.sendMessage("Saving world " + world.getName() + "...");
            world.save();
        });
        sender.sendMessage("Done");
    }
}

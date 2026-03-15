package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.world.World;

public class ViewDistanceCommand implements Command {

    private static final String[] ALIASES = new String[] { "vd" };

    private final MinecraftServer server;

    public ViewDistanceCommand(MinecraftServer server) {
        this.server = server;
    }

    @Override
    public String getName() {
        return "viewdistance";
    }

    @Override
    public String[] getAliases() {
        return ALIASES;
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("/" + getName() + " <world> [distance]");
            return;
        }

        World world = server.getWorldManager().getWorld(args[0]);
        if (world == null) {
            sender.sendMessage("World '" + args[0] + "' not loaded.");
        } else if (args.length >= 2) {
            try {
                int distance = Integer.parseInt(args[1]);
                int actualDistance = world.setViewDistance(distance);
                sender.sendMessage("View distance of world '" + world.getName() + "' set to " + actualDistance);
            } catch (Exception e) {
                sender.sendMessage("'" + args[1] + "' is not a valid distance.");
            }
        } else {
            sender.sendMessage("World '" + world.getName() + "' view distance: " + world.getViewDistance());
        }
    }
}

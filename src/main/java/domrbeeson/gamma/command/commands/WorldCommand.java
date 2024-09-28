package domrbeeson.gamma.command.commands;

import domrbeeson.gamma.command.Command;
import domrbeeson.gamma.command.CommandSender;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.World;
import domrbeeson.gamma.world.WorldManager;

import java.util.Collection;
import java.util.stream.Collectors;

public record WorldCommand(WorldManager worldManager) implements Command {

    @Override
    public String getName() {
        return "world";
    }

    @Override
    public void run(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }

        if (args.length == 0) {
            Collection<World> worlds = worldManager.getWorlds();
            sender.sendMessage("Worlds: [" + worlds.size() + "]");
            String worldList = worlds.stream().map(World::getName).collect(Collectors.joining(", "));
            sender.sendMessage(worldList);
            return;
        }

        String worldName = args[0];
        World world = worldManager.getWorld(worldName);
        if (world == null) {
            sender.sendMessage("World '" + worldName + "' not loaded!");
            return;
        }

        Player player = (Player) sender;
        player.sendMessage("Teleporting to world '" + worldName + "'");
        player.teleport(world, world.getSpawn());
    }

}

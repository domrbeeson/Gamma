package domrbeeson.gamma.command;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.command.commands.*;

import java.util.HashMap;
import java.util.Map;

public final class CommandManager {

    private static final UnknownCommand UNKNOWN_COMMAND = new UnknownCommand();
    private static final Map<String, Command> REGISTERED_COMMANDS = new HashMap<>();

    public CommandManager(MinecraftServer server) {
        register(new BanCommand());
        register(new ClearCommand(server.getPlayerManager()));
        register(new GcCommand());
        register(new ItemCommand(server.getPlayerManager()));
        register(new KickCommand(server.getPlayerManager()));
        register(new ListCommand(server.getPlayerManager()));
        register(new MessageCommand());
        register(new SaveCommand(server));
        register(new StopCommand(server));
        register(new TeleportPosCommand());
        register(new UnbanCommand());
        register(new WorldCommand(server.getWorldManager()));
    }

    public void register(Command command) {
        REGISTERED_COMMANDS.put(command.getName(), command);
        for (String alias : command.getAliases()) {
            REGISTERED_COMMANDS.put(alias, command);
        }
    }

    public void unregister(Command command) {
        REGISTERED_COMMANDS.remove(command.getName());
    }

    public Command get(String name) {
        return REGISTERED_COMMANDS.getOrDefault(name, UNKNOWN_COMMAND);
    }
    
    public void parseAndRun(CommandSender sender, String input) {
        if (input.startsWith("/")) {
            input = input.substring(1);
        }

        String[] parts = input.split(" ");
        Command command = get(parts[0]);
        if (command == null) {
            UNKNOWN_COMMAND.run(sender, new String[0]);
            return;
        }

        String[] args = new String[parts.length - 1];
        System.arraycopy(parts, 1, args, 0, parts.length - 1);
        command.run(sender, args);
    }

}

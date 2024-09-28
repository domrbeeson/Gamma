package domrbeeson.gamma.command;

public interface Command {
    
    String[] EMPTY_ALIASES = new String[0];

    String getName();
    default String[] getAliases() {
        return EMPTY_ALIASES;
    }
    default String getPermission() {
        return null;
    }
    void run(CommandSender sender, String[] args);

}

package domrbeeson.gamma.chat;

public class ChatMessage {

    private final StringBuilder builder = new StringBuilder();

    public ChatMessage() {

    }

    public ChatMessage(String message) {
        builder.append(message);
    }

    public ChatMessage add(ChatColor color) {
        builder.append(color);
        return this;
    }

    public ChatMessage add(String message) {
        builder.append(message);
        return this;
    }

    @Override
    public String toString() {
        return builder.toString();
    }

}

package domrbeeson.gamma.command;

import domrbeeson.gamma.chat.ChatMessage;

public interface CommandSender {

    boolean isPlayer();
    void sendMessage(ChatMessage message);
    void sendMessage(String message);

}

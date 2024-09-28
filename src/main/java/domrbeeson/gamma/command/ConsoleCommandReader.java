package domrbeeson.gamma.command;

import domrbeeson.gamma.Stoppable;
import domrbeeson.gamma.chat.ChatMessage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public final class ConsoleCommandReader implements Runnable, CommandSender, Stoppable {

    private final CommandManager commandManager;
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

    private boolean running = true;

    public ConsoleCommandReader(CommandManager commandManager) {
        this.commandManager = commandManager;
        new Thread(this).start();
    }

    @Override
    public void run() {
        try {
            while (running) {
                while (!reader.ready()) {
                    Thread.sleep(100);
                }
                commandManager.parseAndRun(this, reader.readLine());
            }
        } catch (IOException | InterruptedException e) {
            if (running) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean isPlayer() {
        return false;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        sendMessage(message.toString());
    }

    @Override
    public void sendMessage(String message) {
        System.out.println(message);
    }

    public void stop() {
        running = false;
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

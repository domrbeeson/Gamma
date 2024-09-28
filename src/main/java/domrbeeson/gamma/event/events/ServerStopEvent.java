package domrbeeson.gamma.event.events;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.event.Event;

public class ServerStopEvent implements Event.GlobalEvent {

    private final MinecraftServer server;

    public ServerStopEvent(MinecraftServer server) {
        this.server = server;
    }

    public MinecraftServer getServer() {
        return server;
    }

}

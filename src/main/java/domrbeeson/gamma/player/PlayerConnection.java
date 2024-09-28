package domrbeeson.gamma.player;

import domrbeeson.gamma.network.SocketReader;
import domrbeeson.gamma.network.SocketWriter;

import java.net.Socket;

public class PlayerConnection {

    private final Socket socket;

    private SocketWriter writer;
    private SocketReader reader;

    public PlayerConnection(Socket socket) {
        this.socket = socket;
    }

    protected void setReader(SocketReader reader) {
        this.reader = reader;
    }

    protected void setWriter(SocketWriter writer) {
        this.writer = writer;
    }

    public Socket getSocket() {
        return socket;
    }

    public SocketReader getReader() {
        return reader;
    }

    public SocketWriter getWriter() {
        return writer;
    }

}

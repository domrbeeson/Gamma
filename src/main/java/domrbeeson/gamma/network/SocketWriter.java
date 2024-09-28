package domrbeeson.gamma.network;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.player.PlayerConnection;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Collection;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class SocketWriter implements Runnable {

    private final BlockingQueue<PendingPacket> packetQueue = new LinkedBlockingQueue<>();
    private final MinecraftServer server;
    private final PlayerConnection connection;
    private final Socket socket;
    private final Thread thread;

    public SocketWriter(MinecraftServer server, PlayerConnection connection) {
        this.server = server;
        this.connection = connection;
        socket = connection.getSocket();
        thread = Thread.startVirtualThread(this);
    }

    public void send(int protocol, PacketOut packet) {
        if (!socket.isConnected() || socket.isClosed()) {
            return;
        }
        try {
            packetQueue.put(new PendingPacket(protocol, packet));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void send(PendingPacket packet) {
        try {
            packetQueue.put(packet);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void send(Collection<PendingPacket> packets) {
        packetQueue.addAll(packets);
    }

    @Override
    public void run() {
        DataOutputStream stream;
        PendingPacket pending;
        try {
            while (socket.isConnected() && !socket.isClosed()) {
                pending = packetQueue.take();
                stream = new DataOutputStream(socket.getOutputStream());
                stream.write(pending.packet().getPacket().id);
                pending.packet().send(pending.protocol(), stream);
            }
        } catch (IOException | InterruptedException e) {

        } finally {
            Player player = server.getPlayerManager().get(connection);
            if (player != null) {
                player.remove();
            }
        }
    }

    public void close() {
        thread.interrupt();
    }
}

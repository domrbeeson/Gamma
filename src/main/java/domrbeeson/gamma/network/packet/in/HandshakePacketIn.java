 package domrbeeson.gamma.network.packet.in;

 import domrbeeson.gamma.MinecraftServer;
 import domrbeeson.gamma.network.packet.Packet;
 import domrbeeson.gamma.network.packet.out.HandshakePacketOut;
 import domrbeeson.gamma.player.PlayerConnection;

 import java.io.DataInputStream;
 import java.io.IOException;

 public class HandshakePacketIn extends AsyncPacketIn {

     private final int protocol;
     private final short length = 0;
     private final String username;

    public HandshakePacketIn(MinecraftServer server, PlayerConnection connection, DataInputStream stream) throws IOException {
        super(Packet.HANDSHAKE, server, connection, stream);

        /*
            To detect which string protocol is being used, there has to be no other bytes in the stream, so cannot use readString
            This is very unreliable when the client is in play when other packets are being received, so only detect protocol here
         */
        short length = stream.readShort();
        int available = stream.available();
        if (available == length) {
            // <= Beta 1.4
            protocol = 1;
            username = new String(stream.readNBytes(length));
        } else {
            // >= Beta 1.5
            protocol = 11;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(stream.readChar());
            }
            username = builder.toString();
        }
    }

     @Override
     public void handle() {
         getConnection().getWriter().send(protocol, new HandshakePacketOut());
     }

 }

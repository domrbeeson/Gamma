package domrbeeson.gamma.network.packet.out;

import java.io.DataOutputStream;
import java.io.IOException;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

public class SpawnPositionPacketOut extends PacketOut {

    private final int x, y, z;

    public SpawnPositionPacketOut(Pos pos) {
        super(Packet.SPAWN_POSITION);

        this.x = pos.getBlockX();
        this.y = pos.getBlockY();
        this.z = pos.getBlockZ();
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeInt(x);
        stream.writeInt(y);
        stream.writeInt(z);
    }
    
}

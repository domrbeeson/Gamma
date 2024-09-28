package domrbeeson.gamma.network.packet.out;

import java.io.DataOutputStream;
import java.io.IOException;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.network.packet.Packet;
import domrbeeson.gamma.network.packet.PacketOut;

public class PlayerPositionAndLookPacketOut extends PacketOut {

    private final double x, y, z, stance;
    private final float yaw, pitch;
    private final boolean onGround;

    public PlayerPositionAndLookPacketOut(Pos pos, boolean onGround) {
        super(Packet.PLAYER_POSITION_AND_LOOK);
        this.x = pos.x();
        this.y = pos.y();
        this.z = pos.z();
        this.stance = pos.y() - 1.65;
        this.yaw = pos.yaw();
        this.pitch = pos.pitch();
        this.onGround = onGround;
    }

    @Override
    public void send(int protocol, DataOutputStream stream) throws IOException {
        stream.writeDouble(x);
        stream.writeDouble(y);
        stream.writeDouble(stance);
        stream.writeDouble(z);
        stream.writeFloat(yaw);
        stream.writeFloat(pitch);
        stream.writeBoolean(onGround);
    }
    
}

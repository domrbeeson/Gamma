package domrbeeson.gamma.network;

import domrbeeson.gamma.network.packet.PacketOut;

public record PendingPacket(int protocol, PacketOut packet) {
}

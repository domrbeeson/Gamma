package domrbeeson.gamma.player;

import domrbeeson.gamma.network.packet.out.KeepAlivePacketOut;
import domrbeeson.gamma.task.ScheduledTask;

public class KeepAliveTask extends ScheduledTask {

    private static final KeepAlivePacketOut KEEP_ALIVE_PACKET = new KeepAlivePacketOut();

    private final Player player;

    public KeepAliveTask(Player player) {
        super(20, 20);
        this.player = player;
    }

    @Override
    public void accept(Long tick) {
        player.sendPacket(KEEP_ALIVE_PACKET);
    }
}

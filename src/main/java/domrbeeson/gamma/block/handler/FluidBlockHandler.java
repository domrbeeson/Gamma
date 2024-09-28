package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.player.Player;

public class FluidBlockHandler extends BlockHandler {

    private final int ticksPerFlow;

    public FluidBlockHandler(int ticksPerFlow) {
        this.ticksPerFlow = ticksPerFlow;
    }

    @Override
    public boolean isSolid() {
        return false;
    }

    @Override
    public void onPlace(MinecraftServer server, Block block, Player player) {
        // TODO this is when a player places a fluid source block
        long nextTick = ticksPerFlow + block.world().getTime();
    }

    @Override
    public boolean tick(MinecraftServer server, Block block, long ticks) {
        // TODO water ticks on a schedule, so schedule a new water update here
        long nextTick = ticksPerFlow + block.world().getTime();
        return false;
    }

}

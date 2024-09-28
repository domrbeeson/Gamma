package domrbeeson.gamma.event.events;

import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.event.Event;

public class BlockTickEvent extends CancellableEvent implements Event.WorldEvent {

    private final Block block;

    public BlockTickEvent(Block block) {
        this.block = block;
    }

    public Block getBlock() {
        return block;
    }

}

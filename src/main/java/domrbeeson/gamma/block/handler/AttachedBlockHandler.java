package domrbeeson.gamma.block.handler;

public class AttachedBlockHandler extends BlockHandler {

    private static final byte[] BREAK_WHEN_ADJACENT_IDS = new byte[] {
            0
    };

    @Override
    public boolean isSolid() {
        return false;
    }

//    @Override
//    public void updateAdjacent(Block me, Block adjacent) {
//        // TODO check whether this is the adjacent block this block is attached to
//        byte adjacentId = adjacent.id();
//        for (byte id : BREAK_WHEN_ADJACENT_IDS) {
//            if (id == adjacentId) {
//                // TODO break block, cannot attach to adjacent block
//                return;
//            }
//        }
//    }

}

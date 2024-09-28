package domrbeeson.gamma.world.terrain;

import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;

public class DebugGenerator implements TerrainGenerator {

    private final byte floorBlockId, floorBlockMetadata;

    public DebugGenerator() {
        this(Material.BEDROCK.blockId, (byte) 0);
    }

    public DebugGenerator(byte floorBlockId, byte floorBlockMetadata) {
        this.floorBlockId = floorBlockId;
        this.floorBlockMetadata = floorBlockMetadata;
    }

    @Override
    public void generate(Chunk.Builder chunk) {
        for (byte x = 0; x < Chunk.WIDTH; x++) {
            for (byte z = 0; z < Chunk.WIDTH; z++) {
                chunk.block(x, 0, z, floorBlockId, floorBlockMetadata);
            }
        }
    }

}

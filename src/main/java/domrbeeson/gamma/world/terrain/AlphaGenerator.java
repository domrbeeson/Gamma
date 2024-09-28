package domrbeeson.gamma.world.terrain;

import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;

import java.util.Random;

public class AlphaGenerator implements TerrainGenerator {

//    private static final Structure OAK_TREE = new Structure("OakTree.json");

    private final Random random = new Random();

    @Override
    public void load(World world) {
        random.setSeed(world.getFormat().getSeed());
    }

    @Override
    public void generate(Chunk.Builder builder) {
        for (int i = 0; i < 4; i++) {
            for (int i2 = 0; i2 < 4; i2++) {
                for (int i3 = 0; i3 < 16; i3++) {


                    for (int i4 = 0; i4 < 8; i4++) {


                        for (int i5 = 0; i5 < 4; i5++) {
                            
                        }
                    }
                }
            }
        }

        random.setSeed(builder.x * 341873128712L + builder.z * 132897987541L);
        for (byte x = 0; x < Chunk.WIDTH; x++) {
            for (byte z = 0; z < Chunk.WIDTH; z++) {

                for (int y = 127; y >= 0; y--) {
                    if (y <= random.nextInt(6) - 1) {
                        builder.block(x, y, z, Material.BEDROCK.blockId);
                    }
                }
                builder.block(x, 0, z, Material.BEDROCK.blockId);
            }
        }

        // TODO skyLight
        // TODO blockLight
    }

}

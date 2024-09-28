package domrbeeson.gamma.world.terrain;

import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;

public interface TerrainGenerator {

    default void load(World world) {
        
    }

    void generate(Chunk.Builder builder);

}

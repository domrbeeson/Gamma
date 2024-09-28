package domrbeeson.gamma.world.format;

import domrbeeson.gamma.Saveable;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.Dimension;
import domrbeeson.gamma.world.World;

public interface WorldFormat extends Saveable {

    boolean exists(World world);
    void create(World world);
    void load(World world);
    boolean readChunk(Chunk.Builder chunk);
    void writeChunk(Chunk chunk);

    Pos getSpawn();
    void setSpawn(Pos spawn);

    long getSeed();
    Dimension getDimension();

}

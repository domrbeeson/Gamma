package domrbeeson.gamma.world.format;

import domrbeeson.gamma.Saveable;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.Dimension;
import domrbeeson.gamma.world.World;

public interface WorldFormat extends Saveable {

    void load(World world);
    boolean readChunk(Chunk.Builder chunk, Chunk chunkReference);
    void writeChunk(Chunk chunk);
    Player readPlayer(Player.Builder builder);
    void writePlayer(Player player);

    Pos getSpawn();
    void setSpawn(Pos spawn);

    long getSeed();
    Dimension getDimension();

}

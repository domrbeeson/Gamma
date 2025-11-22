package domrbeeson.gamma.world.format;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.Dimension;
import domrbeeson.gamma.world.World;

public class ReadOnlyWorldFormat implements WorldFormat {

    private final WorldFormat worldFormat;

    public ReadOnlyWorldFormat(WorldFormat worldFormat) {
        this.worldFormat = worldFormat;
    }

    @Override
    public void load(World world) {
        worldFormat.load(world);
    }

    @Override
    public boolean readChunk(Chunk.Builder builder, Chunk chunkReference) {
        return worldFormat.readChunk(builder, chunkReference);
    }

    @Override
    public void writeChunk(Chunk chunk) {

    }

    @Override
    public Player readPlayer(Player.Builder builder) {
        return worldFormat.readPlayer(builder);
    }

    @Override
    public void writePlayer(Player player) {

    }

    @Override
    public long getSeed() {
        return worldFormat.getSeed();
    }

    @Override
    public Dimension getDimension() {
        return worldFormat.getDimension();
    }

    @Override
    public Pos getSpawn() {
        return worldFormat.getSpawn();
    }

    @Override
    public void setSpawn(Pos spawn) {
        worldFormat.setSpawn(spawn);
    }

    @Override
    public void save() {
        
    }

}

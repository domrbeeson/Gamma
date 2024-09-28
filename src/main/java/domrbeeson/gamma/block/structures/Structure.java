package domrbeeson.gamma.block.structures;

import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.utils.ResourceUtil;
import domrbeeson.gamma.world.Chunk;
import domrbeeson.gamma.world.World;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class Structure {

    record StructureBlock(int xOffset, int yOffset, int zOffset, byte id, byte metadata) {}

    private final StructureBlock[] blocks;

    public Structure(String structureFile) {
        this.blocks = readFromResource(structureFile);
    }

    public void place(World world, Pos pos) {
        Chunk chunk;
        Pos blockPos;
        for (StructureBlock sb : blocks) {
            blockPos = pos.add(new Pos(sb.xOffset, sb.yOffset, sb.zOffset));
            chunk = world.getChunk(blockPos.getChunkX(), blockPos.getChunkZ()).join();
            chunk.directlySetBlock(Block.getChunkRelativeX(blockPos.getBlockX()), blockPos.getBlockY(), Block.getChunkRelativeZ(blockPos.getBlockZ()), sb.id, sb.metadata);
        }
    }

    private StructureBlock[] readFromResource(String structureFile) {
        StructureBlock[] blocks;
        try (InputStream stream = ResourceUtil.getResource("structures/" + structureFile)) { // TODO null pointer
            JSONArray array = new JSONArray(stream);
            blocks = new StructureBlock[array.length()];
            int i = 0;
            for (Object o : array) {
                JSONObject blockJson = (JSONObject) o;
                int x = blockJson.getInt("x");
                int y = blockJson.getInt("y");
                int z = blockJson.getInt("z");
                byte id = (byte) blockJson.getInt("id");
                byte metadata = (byte) blockJson.getInt("meta");
                blocks[i] = new StructureBlock(x, y, z, id, metadata);
                i++;
            }
        } catch (IOException e) {
            e.printStackTrace();
            blocks = new StructureBlock[0];
        }
        return blocks;
    }

}

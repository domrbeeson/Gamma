package domrbeeson.gamma.block;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.handler.*;
import domrbeeson.gamma.item.Material;

import static domrbeeson.gamma.block.handler.BlockHandler.updateAdjacentBlocks;

public final class BlockHandlers {

    public static class EmptyBlockHandler extends BlockHandler {
        @Override
        public boolean isSolid() {
            return false;
        }
    }
    private static final EmptyBlockHandler EMPTY_BLOCK_HANDLER = new EmptyBlockHandler();
    private static final SelfDropBlockHandler DEFAULT_BLOCK_HANDLER = new SelfDropBlockHandler();

    private final BlockHandler[] handlers = new BlockHandler[96];
    private final MinecraftServer server;

    public BlockHandlers(MinecraftServer server) {
        this.server = server;

        AttachedBlockHandler attachedBlockHandler = new AttachedBlockHandler();
        MushroomBlockHandler mushroomBlockHandler = new MushroomBlockHandler();
        FluidBlockHandler waterBlockHandler = new FluidBlockHandler(5);
        FluidBlockHandler lavaBlockHandler = new FluidBlockHandler(30); // TODO 10 ticks in nether
        InstantBreakBlockHandler instantBreakHandler = new InstantBreakBlockHandler();

        register(Material.AIR, new AirBlockHandler());
        register(Material.STONE, new ToolsDropBlockHandler(Material.COBBLESTONE, Material.WOOD_PICKAXE.id, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.GOLD_PICKAXE.id, Material.DIAMOND_PICKAXE.id));
        register(Material.GRASS, new GrassBlockHandler());
        register(Material.DIRT, DEFAULT_BLOCK_HANDLER);
        register(Material.COBBLESTONE, new ToolsDropBlockHandler(Material.COBBLESTONE, Material.WOOD_PICKAXE.id, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.GOLD_PICKAXE.id, Material.DIAMOND_PICKAXE.id));
        register(Material.OAK_PLANKS, DEFAULT_BLOCK_HANDLER);
        register(Material.OAK_SAPLING, new SaplingBlockHandler());
        register(Material.WATER_FLOWING, waterBlockHandler);
        register(Material.WATER_SOURCE, waterBlockHandler);
        register(Material.LAVA_FLOWING, lavaBlockHandler);
        register(Material.LAVA_SOURCE, lavaBlockHandler);
        register(Material.SAND, new FallingBlockHandler(Material.SAND));
        register(Material.GRAVEL, new GravelBlockHandler());
        register(Material.GOLD_ORE, new ToolsDropBlockHandler(Material.GOLD_ORE, Material.IRON_PICKAXE.id, Material.DIAMOND_PICKAXE.id, Material.GOLD_PICKAXE.id));
        register(Material.IRON_ORE, new ToolsDropBlockHandler(Material.IRON_ORE, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.DIAMOND_PICKAXE.id));
        register(Material.COAL_ORE, new ToolsDropBlockHandler(Material.COAL, Material.WOOD_PICKAXE.id, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.DIAMOND_PICKAXE.id, Material.GOLD_PICKAXE.id));
        register(Material.OAK_LOG, DEFAULT_BLOCK_HANDLER);
        register(Material.OAK_LEAVES, new LeafBlockHandler());
        register(Material.SPONGE, new SelfDropBlockHandler());
        register(Material.LAPIS_ORE, new LapisOreBlockHandler());
        register(Material.LAPIS_BLOCK, new ToolsDropBlockHandler(Material.LAPIS_BLOCK, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.DIAMOND_PICKAXE.id));
        register(Material.CLAY_BLOCK, new ClayBlockHandler());
        register(Material.MOSSY_COBBLESTONE, new ToolsDropBlockHandler(Material.MOSSY_COBBLESTONE, Material.WOOD_PICKAXE.id, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.DIAMOND_PICKAXE.id, Material.GOLD_PICKAXE.id));

        register(Material.CHEST, new ChestBlockHandler());

        register(Material.CRAFTING_TABLE, new CraftingTableBlockHandler());
        register(Material.FURNACE, new FurnaceBlockHandler());
        register(Material.FURNACE_BURNING, new FurnaceBlockHandler());

        register(Material.WHEAT_CROPS, new WheatBlockHandler());

        register(Material.SUGAR_CANE_BLOCK, new SugarCaneBlockHandler());
        register(Material.CACTUS, new CactusBlockHandler());

        register(Material.DEFAULT_FERN, attachedBlockHandler);
        register(Material.BROWN_MUSHROOM, mushroomBlockHandler);
        register(Material.RED_MUSHROOM, mushroomBlockHandler);
        register(Material.DANDELION, instantBreakHandler);
        register(Material.ROSE, instantBreakHandler);
        register(Material.PUMPKIN, DEFAULT_BLOCK_HANDLER);

        register(Material.SNOW_LAYER, new SnowLayerBlockHandler());
    }

    public void register(Material material, BlockHandler handler) {
        handlers[material.blockId] = handler;
    }

    public void unregister(short id) {
        handlers[id] = null;
    }

    public BlockHandler get(byte id) {
        if (id >= handlers.length || id < 1) {
            return EMPTY_BLOCK_HANDLER;
        }
        if (handlers[id] == null) {
            return DEFAULT_BLOCK_HANDLER;
        }
        return handlers[id];
    }

    public void tick(Block block) {
        if (block == null) {
            return;
        }
        if (get(block.id()).tick(server, block, block.world().getTime())) {
            updateAdjacentBlocks(block, 1);
        }
    }

}

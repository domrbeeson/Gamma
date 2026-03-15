package domrbeeson.gamma.block;

import domrbeeson.gamma.block.handler.*;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.Dimension;

import java.util.HashMap;

public final class BlockHandlers {

    public static class EmptyBlockHandler implements BlockHandler {
        @Override
        public boolean isSolid() {
            return false;
        }
    }

    private static final EmptyBlockHandler EMPTY_BLOCK_HANDLER = new EmptyBlockHandler();
    private static final BlockHandler[] HANDLERS = new BlockHandler[96];

    static {
        AttachedBlockHandler attachedBlockHandler = new AttachedBlockHandler();
        MushroomBlockHandler mushroomBlockHandler = new MushroomBlockHandler();
        FluidBlockHandler waterBlockHandler = new FluidBlockHandler(Material.WATER_SOURCE.blockId, Material.WATER_FLOWING.blockId, 5, (byte) 1);
        FluidBlockHandler lavaBlockHandler = new FluidBlockHandler(Material.LAVA_SOURCE.blockId, Material.LAVA_FLOWING.blockId, new HashMap<>() {{
                put(Dimension.OVERWORLD, 30L);
                put(Dimension.NETHER, 10L);
            }}, new HashMap<>() {{
                put(Dimension.OVERWORLD, (byte) 2);
                put(Dimension.NETHER, (byte) 1);
            }}
        );
        FarmlandBlockHandler farmlandBlockHandler = new FarmlandBlockHandler();
        FlowerBlockHandler flowerBlockHandler = new FlowerBlockHandler();
        SelfDropBlockHandler selfDropBlockHandler = new SelfDropBlockHandler();

        register(Material.AIR, new AirBlockHandler());
        register(Material.STONE, new ToolsDropBlockHandler(Material.COBBLESTONE, Material.WOOD_PICKAXE.id, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.GOLD_PICKAXE.id, Material.DIAMOND_PICKAXE.id));
        register(Material.GRASS, new GrassBlockHandler());
        register(Material.DIRT, farmlandBlockHandler);
        register(Material.COBBLESTONE, new ToolsDropBlockHandler(Material.COBBLESTONE, Material.WOOD_PICKAXE.id, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.GOLD_PICKAXE.id, Material.DIAMOND_PICKAXE.id));
        register(Material.OAK_PLANKS, selfDropBlockHandler);
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
        register(Material.OAK_LOG, selfDropBlockHandler);
        register(Material.OAK_LEAVES, new LeafBlockHandler());
        register(Material.SPONGE, new SelfDropBlockHandler());
        register(Material.LAPIS_ORE, new LapisOreBlockHandler());
        register(Material.LAPIS_BLOCK, new ToolsDropBlockHandler(Material.LAPIS_BLOCK, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.DIAMOND_PICKAXE.id));
        register(Material.CLAY_BLOCK, new ClayBlockHandler());
        register(Material.MOSSY_COBBLESTONE, new ToolsDropBlockHandler(Material.MOSSY_COBBLESTONE, Material.WOOD_PICKAXE.id, Material.STONE_PICKAXE.id, Material.IRON_PICKAXE.id, Material.DIAMOND_PICKAXE.id, Material.GOLD_PICKAXE.id));

        register(Material.FIRE, new InstantBreakBlockHandler());

        register(Material.CHEST, new ChestBlockHandler());
        register(Material.REDSTONE_WIRE, new RedstoneBlockHandler());
        register(Material.REDSTONE_TORCH, new RedstoneTorchBlockHandler());

        register(Material.CRAFTING_TABLE, new CraftingTableBlockHandler());
        register(Material.FARMLAND, farmlandBlockHandler);
        register(Material.FURNACE, new FurnaceBlockHandler());
        register(Material.FURNACE_BURNING, new FurnaceBlockHandler());

        register(Material.WHEAT_CROPS, new WheatBlockHandler());

        register(Material.SUGAR_CANE_BLOCK, new SugarCaneBlockHandler());
        register(Material.CACTUS, new CactusBlockHandler());

        register(Material.DEFAULT_FERN, attachedBlockHandler);
        register(Material.BROWN_MUSHROOM, mushroomBlockHandler);
        register(Material.RED_MUSHROOM, mushroomBlockHandler);
        register(Material.DANDELION, flowerBlockHandler);
        register(Material.ROSE, flowerBlockHandler);

        SignBlockHandler signBlockHandler = new SignBlockHandler();
        register(Material.SIGN_POST, signBlockHandler);
        register(Material.WALL_SIGN, signBlockHandler);

        register(Material.PUMPKIN, selfDropBlockHandler);

        register(Material.SNOW_LAYER, new SnowLayerBlockHandler());

        register(Material.TNT, new TntBlockHandler());

        for (int i = 0; i < HANDLERS.length; i++) {
            if (HANDLERS[i] == null) {
                HANDLERS[i] = EMPTY_BLOCK_HANDLER;
            }
        }
    }

    public static void register(Material material, BlockHandler handler) {
        HANDLERS[material.blockId] = handler;
    }

    public static void unregister(short id) {
        HANDLERS[id] = null;
    }

    public static BlockHandler getBlockHandler(byte id) {
        if (id >= HANDLERS.length || id < 0) {
            return EMPTY_BLOCK_HANDLER;
        }
        return HANDLERS[id];
    }

    public static BlockHandler getBlockHandler(Material material) {
        return getBlockHandler(material.blockId);
    }

}

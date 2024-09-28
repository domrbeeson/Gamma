package domrbeeson.gamma.version;

import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.fuel.CoalFuel;
import domrbeeson.gamma.fuel.LavaBucketFuel;
import domrbeeson.gamma.fuel.LogFuel;
import domrbeeson.gamma.fuel.SaplingFuel;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum MinecraftVersion {
    ALPHA_1_0_17_02("Alpha 1.0.17_02", new Features.Builder(1)
            .materials(List.of(
                    Material.GRASS,
                    Material.STONE,
                    Material.DIRT,
                    Material.OAK_PLANKS,
                    Material.STONE_DOUBLE_SLAB,
                    Material.STONE_SLAB,
                    Material.BRICK_BLOCK,
                    Material.TNT,
                    Material.ROSE,
                    Material.DANDELION,
                    Material.OAK_SAPLING,
                    Material.COBBLESTONE,
                    Material.BEDROCK,
                    Material.SAND,
                    Material.GRAVEL,
                    Material.OAK_LOG,
                    Material.IRON_BLOCK,
                    Material.GOLD_BLOCK,
                    Material.DIAMOND_BLOCK,
                    Material.CHEST,
                    Material.RED_MUSHROOM,
                    Material.BROWN_MUSHROOM,
                    Material.GOLD_ORE,
                    Material.IRON_ORE,
                    Material.COAL_ORE,
                    Material.BOOKSHELF,
                    Material.MOSSY_COBBLESTONE,
                    Material.OBSIDIAN,
                    Material.CRAFTING_TABLE,
                    Material.FURNACE,
                    Material.FURNACE_BURNING,
                    Material.SPONGE,
                    Material.GLASS,
                    Material.DIAMOND_ORE,
                    Material.REDSTONE_ORE,
                    Material.REDSTONE_ORE_GLOWING,
                    Material.OAK_LEAVES,
                    Material.WHITE_WOOL,
                    Material.ORANGE_WOOL,
                    Material.MAGENTA_WOOL,
                    Material.LIGHT_BLUE_WOOL,
                    Material.YELLOW_WOOL,
                    Material.LIME_WOOL,
                    Material.PINK_WOOL,
                    Material.DARK_GREY_WOOL,
                    Material.GREY_WOOL,
                    Material.CYAN_WOOL,
                    Material.PURPLE_WOOL,
                    Material.BLUE_WOOL,
                    Material.BROWN_WOOL,
                    Material.GREEN_WOOL,
                    Material.RED_WOOL,
                    Material.BLACK_WOOL,
                    Material.MONSTER_SPAWNER,
                    Material.SNOW_BLOCK,
                    Material.SNOW_LAYER,
                    Material.ICE,
                    Material.CACTUS,
                    Material.CLAY_BLOCK,
                    Material.SUGAR_CANE_BLOCK,
                    Material.NOTE_BLOCK,
                    Material.JUKEBOX,
                    Material.FENCE,
                    Material.TORCH,
                    Material.OAK_DOOR,
                    Material.IRON_DOOR,
                    Material.LADDER,
                    Material.REDSTONE_WIRE,
                    Material.FARMLAND,
                    Material.WHEAT_CROPS,
                    Material.REDSTONE_TORCH,
                    Material.REDSTONE_TORCH_OFF,
                    Material.RAIL,
                    Material.LEVER,
                    Material.WATER_FLOWING,
                    Material.WATER_SOURCE,
                    Material.LAVA_FLOWING,
                    Material.LAVA_SOURCE
            ))
            .entities(List.of(
                    EntityType.CHICKEN,
                    EntityType.COW,
                    EntityType.CREEPER,
                    EntityType.PLAYER,
                    EntityType.PIG,
                    EntityType.PLAYER,
                    EntityType.SHEEP,
                    EntityType.SKELETON,
                    EntityType.SPIDER,
                    EntityType.ZOMBIE,
                    EntityType.ARROW,
                    EntityType.BOAT,
                    EntityType.SAND,
                    EntityType.GRAVEL,
                    EntityType.ITEM,
                    EntityType.MINECART,
                    EntityType.PAINTING,
                    EntityType.SNOWBALL,
                    EntityType.PRIMED_TNT
            ))
            .fuel(List.of(
                    new LavaBucketFuel(),
                    new CoalFuel(),
                    new LogFuel()
            ))
    .build()),
    ALPHA_1_1_1("Alpha 1.1.1", new Features.Builder(2)
            .extend(ALPHA_1_0_17_02)
            .sneak()
            .materials(List.of(
                    Material.COMPASS,
                    Material.FISHING_ROD,
                    Material.PAINTING
            ))
            .entity(EntityType.PAINTING)
    .build()),
    ALPHA_1_2_0("Alpha 1.2.0", new Features.Builder(3)
            .extend(ALPHA_1_1_1)
            .nether()
            .fishing()
            .materials(List.of(
                    Material.NETHERRACK,
                    Material.SOUL_SAND,
                    Material.GLOWSTONE,
                    Material.PUMPKIN,
                    Material.JACK_O_LANTERN,
                    Material.PORTAL,
                    Material.CLOCK,
                    Material.GLOWSTONE_DUST,
                    Material.FISH,
                    Material.COOKED_FISH
            ))
            .entities(List.of(
                    EntityType.GHAST,
                    EntityType.ZOMBIE_PIGMAN
            ))
    .build()),
    ALPHA_1_2_2("Alpha 1.2.2", new Features.Builder(4)
            .extend(ALPHA_1_2_0)
            // TODO extended lava flow in the nether
    .build()),
    ALPHA_1_2_3("Alpha 1.2.3", new Features.Builder(5)
            .extend(ALPHA_1_2_2)
    .build()),
    ALPHA_1_2_3_05("Alpha 1.2.3_05", new Features.Builder(6)
            .extend(ALPHA_1_2_3)
    .build()),
    BETA_1_0("Beta 1.0", new Features.Builder(7)
            .extend(ALPHA_1_2_3_05)
            .throwEggs()
            // TODO tools thrown on ground don't repair themselves?
    .build()),
    BETA_1_2("Beta 1.2", new Features.Builder(8)
            .extend(BETA_1_0)
            .materials(List.of(Material.CAKE,
                    Material.DISPENSER,
                    Material.LAPIS_BLOCK,
                    Material.LAPIS_ORE,
                    Material.NOTE_BLOCK,
                    Material.SANDSTONE,
                    Material.SPRUCE_LOG,
                    Material.BIRCH_LOG,
                    Material.SPRUCE_LEAVES,
                    Material.BIRCH_LEAVES,
                    Material.BONE,
                    Material.BONE_MEAL,
                    Material.COCOA_BEANS,
                    Material.INK_SAC,
                    Material.CHARCOAL,
                    Material.SUGAR
            ))
            .entities(List.of(
                    EntityType.SQUID,
                    EntityType.SLIME
            ))
    .build()),
    BETA_1_3("Beta 1.3", new Features.Builder(9)
            .extend(BETA_1_2)
            .materials(List.of(
                    Material.BED,
                    Material.REDSTONE_REPEATER,
                    Material.COBBLESTONE_SLAB,
                    Material.COBBLESTONE_DOUBLE_SLAB,
                    Material.OAK_SLAB,
                    Material.OAK_DOUBLE_SLAB,
                    Material.SANDSTONE_SLAB,
                    Material.SANDSTONE_DOUBLE_SLAB
            ))
    .build()),
    BETA_1_4("Beta 1.4", new Features.Builder(10)
            .extend(BETA_1_3)
            .materials(List.of(
                    Material.LOCKED_CHEST,
                    Material.COOKIE
            ))
            .entity(EntityType.WOLF)
    .build()),
    // TODO from beta 1.5 there is a new login protocol, see https://web.archive.org/web/20110504134842/http://wiki.vg/Protocol#Login_Request_.280x01.29
    BETA_1_5("Beta 1.5", new Features.Builder(11)
            .extend(BETA_1_4)
            .weather()
            .materials(List.of(
                    Material.BIRCH_SAPLING,
                    Material.COBWEB,
                    Material.DETECTOR_RAIL,
                    Material.POWERED_RAIL,
                    Material.SPRUCE_SAPLING
            ))
//            .entity(EntityType.CHARGED_CREEPER) // TODO
            .fuel(List.of(
                    new SaplingFuel()
            ))
    .build()),
    BETA_1_6("Beta 1.6", new Features.Builder(13)
            .extend(BETA_1_5)
            .materials(List.of(
                    Material.DEAD_BUSH,
                    Material.SHORT_GRASS,
                    Material.FERN,
                    Material.TRAPDOOR,
                    Material.MAP
            ))
//            .recipe(new CraftingRecipe(Material.GLOWSTONE,
//                    Material.GLOWSTONE_DUST, Material.GLOWSTONE_DUST, Material.AIR,
//                    Material.GLOWSTONE_DUST, Material.GLOWSTONE_DUST
//            ))
//            .recipe(new CraftingRecipe(Material.WHITE_WOOL,
//                    Material.STRING, Material.STRING, Material.AIR,
//                    Material.STRING, Material.STRING
//            ))
    .build()),
    BETA_1_7("Beta 1.7", new Features.Builder(14)
            .extend(BETA_1_6)
            .tntPrimeWithFlintAndSteel()
            .sheepRequiresShears()
            .materials(List.of(
                    Material.PISTON,
                    Material.STICKY_PISTON,
                    Material.SHEARS
            ))
    .build())
    ;

    private static final Map<Integer, MinecraftVersion> VERSIONS = new HashMap<>();

    public final String name;
    public final Features features;

    MinecraftVersion(String name, Features features) {
        this.name = name;
        this.features = features;
    }

    @Nullable
    public static MinecraftVersion getVersion(int protocol) {
        return VERSIONS.get(protocol);
    }

    static {
        for (MinecraftVersion version : values()) {
            VERSIONS.put(version.features.protocol(), version);
        }
    }
}

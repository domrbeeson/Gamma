package domrbeeson.gamma.item;

import domrbeeson.gamma.crafting.CraftingRecipe;
import domrbeeson.gamma.item.items.*;

import java.util.*;
import java.util.function.Function;

public enum Material {

    // TODO max stacks

    AIR(0),
    STONE(1),
    GRASS(2),
    DIRT(3),
    COBBLESTONE(new Builder(4).itemCreator(CobblestoneItem::new)),
    OAK_PLANKS(5),
    OAK_SAPLING(6),
    SPRUCE_SAPLING(new Builder(6).metadata(1)),
    BIRCH_SAPLING(new Builder(6).metadata(2)),
    BEDROCK(7),
    WATER_FLOWING(8),
    WATER_SOURCE(9),
    LAVA_FLOWING(10),
    LAVA_SOURCE(11),
    SAND(12),
    GRAVEL(13),
    GOLD_ORE(14),
    IRON_ORE(15),
    COAL_ORE(16),
    OAK_LOG(17),
    SPRUCE_LOG(new Builder(17).metadata(1)),
    BIRCH_LOG(new Builder(17).metadata(2)),
    OAK_LEAVES(18),
    SPRUCE_LEAVES(new Builder(18).metadata(1)),
    BIRCH_LEAVES(new Builder(18).metadata(2)),
    SPONGE(19),
    GLASS(20),
    LAPIS_ORE(21),
    LAPIS_BLOCK(22),
    DISPENSER(23),
    SANDSTONE(24),
    NOTE_BLOCK(25),
    // BED_BOTTOM
    POWERED_RAIL(27),
    DETECTOR_RAIL(28),
    STICKY_PISTON(29),
    COBWEB(30),
    DEFAULT_FERN(31),
    SHORT_GRASS(new Builder(31).metadata(1)),
    FERN(new Builder(31).metadata(2)),
    SNOW_FERN(new Builder(31).metadata(3)),
    DEAD_BUSH(32),
    // SHRUB
    PISTON(33),
    // PISTON_EXTENSION
    WHITE_WOOL(35),
    ORANGE_WOOL(new Builder(35).metadata(1)),
    MAGENTA_WOOL(new Builder(35).metadata(2)),
    LIGHT_BLUE_WOOL(new Builder(35).metadata(3)),
    YELLOW_WOOL(new Builder(35).metadata(4)),
    LIME_WOOL(new Builder(35).metadata(5)),
    PINK_WOOL(new Builder(35).metadata(6)),
    DARK_GREY_WOOL(new Builder(35).metadata(7)),
    GREY_WOOL(new Builder(35).metadata(8)),
    CYAN_WOOL(new Builder(35).metadata(9)),
    PURPLE_WOOL(new Builder(35).metadata(10)),
    BLUE_WOOL(new Builder(35).metadata(11)),
    BROWN_WOOL(new Builder(35).metadata(12)),
    GREEN_WOOL(new Builder(35).metadata(13)),
    RED_WOOL(new Builder(35).metadata(14)),
    BLACK_WOOL(new Builder(35).metadata(15)),
    PISTON_EXTENSION(36),
    DANDELION(37),
    ROSE(38),
    BROWN_MUSHROOM(39),
    RED_MUSHROOM(40),
    GOLD_BLOCK(41),
    IRON_BLOCK(42),
    STONE_DOUBLE_SLAB(43),
    SANDSTONE_DOUBLE_SLAB(new Builder(43).metadata(1)),
    OAK_DOUBLE_SLAB(new Builder(43).metadata(2)),
    COBBLESTONE_DOUBLE_SLAB(new Builder(43).metadata(3)),
    STONE_SLAB(44),
    SANDSTONE_SLAB(new Builder(44).metadata(1)),
    OAK_SLAB(new Builder(44).metadata(2)),
    COBBLESTONE_SLAB(new Builder(44).metadata(3)),
    BRICK_BLOCK(45),
    TNT(46),
    BOOKSHELF(47),
    MOSSY_COBBLESTONE(48),
    OBSIDIAN(49),
    TORCH(50),
    FIRE(51),
    MONSTER_SPAWNER(52),
    OAK_STAIRS(53),
    CHEST(54),
    REDSTONE_WIRE(55),
    DIAMOND_ORE(56),
    DIAMOND_BLOCK(57),
    CRAFTING_TABLE(58),
    WHEAT_CROPS(59),
    FARMLAND(60),
    FURNACE(61),
    FURNACE_BURNING(62),
    SIGN_POST(63),
    OAK_DOOR_BOTTOM(64),
    LADDER(65),
    RAIL(66),
    COBBLESTONE_STAIRS(67),
    LEVER(69),
    STONE_PRESSURE_PLATE(70),
    IRON_DOOR_BOTTOM(71),
    OAK_PRESSURE_PLATE(72),
    REDSTONE_ORE(73),
    REDSTONE_ORE_GLOWING(74),
    REDSTONE_TORCH_OFF(75),
    REDSTONE_TORCH(76),
    STONE_BUTTON(77),
    SNOW_LAYER(78),
    ICE(79),
    SNOW_BLOCK(80),
    CACTUS(81),
    CLAY_BLOCK(82),
    SUGAR_CANE_BLOCK(83),
    JUKEBOX(84),
    FENCE(85),
    PUMPKIN(86),
    NETHERRACK(87),
    SOUL_SAND(88),
    GLOWSTONE(89),
    PORTAL(90),
    JACK_O_LANTERN(91),
    CAKE_BLOCK(92),
    LOCKED_CHEST(95),
    TRAPDOOR(96),

    IRON_SHOVEL(new Builder(256).maxStack(1)),
    IRON_PICKAXE(new Builder(257).maxStack(1)),
    IRON_AXE(258),
    FLINT_AND_STEEL(259),
    APPLE(new Builder(260).maxStack(1).itemCreator(AppleItem::new)),
    BOW(261),
    ARROW(262),
    COAL(263),
    CHARCOAL(new Builder(263).metadata(1)),
    DIAMOND(264),
    IRON_INGOT(265),
    GOLD_INGOT(266),
    IRON_SWORD(267),
    WOOD_SWORD(268),
    WOOD_SHOVEL(269),
    WOOD_PICKAXE(270),
    WOOD_AXE(271),
    STONE_SWORD(272),
    STONE_SHOVEL(273),
    STONE_PICKAXE(274),
    STONE_AXE(275),
    DIAMOND_SWORD(276),
    DIAMOND_SHOVEL(277),
    DIAMOND_PICKAXE(278),
    DIAMOND_AXE(279),
    STICK(280),
    BOWL(281),
    MUSHROOM_SOUP(new Builder(282).maxStack(1).itemCreator(MushroomSoupItem::new)),
    GOLD_SWORD(283),
    GOLD_SHOVEL(284),
    GOLD_PICKAXE(285),
    GOLD_AXE(286),
    STRING(287),
    FEATHER(288),
    GUNPOWDER(289),
    WOOD_HOE(290),
    STONE_HOE(291),
    IRON_HOE(292),
    DIAMOND_HOE(293),
    GOLD_HOE(294),
    WHEAT_SEEDS(295),
    WHEAT(296),
    BREAD(new Builder(297).maxStack(1).itemCreator(BreadItem::new)),
    LEATHER_HELMET(298),
    LEATHER_TUNIC(299),
    LEATHER_LEGS(300),
    LEATHER_BOOTS(301),
    CHAIN_HELMET(302),
    CHAIN_CHEST(303),
    CHAIN_LEGS(304),
    CHAIN_BOOTS(305),
    IRON_HELMET(306),
    IRON_CHEST(307),
    IRON_LEGS(308),
    IRON_BOOTS(309),
    DIAMOND_HELMET(310),
    DIAMOND_CHEST(311),
    DIAMOND_LEGS(312),
    DIAMOND_BOOTS(313),
    GOLD_HELMET(314),
    GOLD_CHEST(315),
    GOLD_LEGS(316),
    GOLD_BOOTS(317),
    FLINT(318),
    RAW_PORK(new Builder(319).maxStack(1).itemCreator(RawPorkItem::new)),
    COOKED_PORK(new Builder(320).maxStack(1).itemCreator(CookedPorkItem::new)),
    PAINTING(321),
    GOLDEN_APPLE(new Builder(322).maxStack(1).itemCreator(GoldenAppleItem::new)),
    NOTCH_APPLE(new Builder(322).metadata(1).maxStack(1)), // TODO item creator
    SIGN(323),
    OAK_DOOR(324),
    BUCKET(325),
    WATER_BUCKET(326),
    LAVA_BUCKET(327),
    MINECART(328),
    SADDLE(329),
    IRON_DOOR(330),
    REDSTONE(331),
    SNOWBALL(332),
    BOAT(333),
    LEATHER(334),
    MILK_BUCKET(335),
    BRICK(336),
    CLAY(337),
    SUGAR_CANE(338),
    PAPER(339),
    BOOK(340),
    SLIME_BALL(341),
    CHEST_MINECART(342),
    FURNACE_MINECART(343),
    EGG(344),
    COMPASS(345),
    FISHING_ROD(346),
    CLOCK(347),
    GLOWSTONE_DUST(348),
    FISH(new Builder(349).maxStack(1).itemCreator(FishItem::new)),
    COOKED_FISH(new Builder(350).maxStack(1).itemCreator(CookedFishItem::new)),
    INK_SAC(351),
    ROSE_RED(new Builder(351).metadata(1)),
    CACTUS_GREEN(new Builder(351).metadata(2)),
    COCOA_BEANS(new Builder(351).metadata(3)),
    LAPIS_LAZULI(new Builder(351).metadata(4)),
    PURPLE_DYE(new Builder(351).metadata(5)),
    CYAN_DYE(new Builder(351).metadata(6)),
    LIGHT_GREY_DYE(new Builder(351).metadata(7)),
    GREY_DYE(new Builder(351).metadata(8)),
    PINK_DYE(new Builder(351).metadata(9)),
    LIME_DYE(new Builder(351).metadata(10)),
    YELLOW_DYE(new Builder(351).metadata(11)),
    LIGHT_BLUE_DYE(new Builder(351).metadata(12)),
    MAGENTA_DYE(new Builder(351).metadata(13)),
    ORANGE_DYE(new Builder(351).metadata(14)),
    BONE_MEAL(new Builder(351).metadata(15)),
    BONE(352),
    SUGAR(353),
    CAKE(new Builder(354).maxStack(1).blockId(CAKE_BLOCK.blockId)),
    BED(new Builder(355).maxStack(1)),
    REDSTONE_REPEATER(new Builder(356).maxStack(1)),
    COOKIE(new Builder(357).maxStack(8)),
    MAP(new Builder(358).maxStack(1)),
    SHEARS(new Builder(359).maxStack(1)),
    DISC_11(new Builder(2256).maxStack(1)),
    DISC_CAT(new Builder(2257).maxStack(1)),
    ;

//    private static final Material[] MATERIALS = new Material[2258];
    private static final Map<Integer, Material> MATERIAL_BY_ID_AND_META = new HashMap<>();

    public final short id;
    public final short metadata;
    public final boolean block;
    public final short maxStack;
    public final byte blockId;
    public final CraftingRecipe[] recipes;
    private final Function<Short, Item> itemCreator;

    Material(int id) {
        this((short) id, (short) 0, (byte) 64, (byte) 0, new CraftingRecipe[0], null);
    }

    Material(Builder builder) {
        this(builder.id, builder.metadata, builder.maxStack, builder.blockId, builder.recipes.toArray(new CraftingRecipe[0]), builder.itemCreator);
    }

    Material(short id, short metadata, byte maxStack, byte blockId, CraftingRecipe[] recipes, Function<Short, Item> itemCreator) {
        this.id = id;
        this.metadata = metadata;
        this.maxStack = maxStack;
        this.recipes = recipes;

        block = id <= Byte.MAX_VALUE && id > 0;
        if (block) {
            this.blockId = (byte) id;
        } else {
            this.blockId = blockId;
        }

        if (itemCreator != null) {
            this.itemCreator = itemCreator;
        } else {
            this.itemCreator = amount -> new Item(id, metadata, amount); // TODO support setting amount to 0 and it just changes to air
        }
    }

    public Item getItem() {
        return getItem(1);
    }

    public Item getItem(int amount) {
        return getItem((short) amount);
    }

    public Item getItem(short amount) {
        return itemCreator.apply(amount);
    }

    public static Material get(short id, short metadata) {
//        if (id >= MATERIALS.length || id < 0) {
//            return AIR;
//        }
//
//        return MATERIALS[id];
        return MATERIAL_BY_ID_AND_META.getOrDefault(id << 16 | metadata, AIR);
    }

    static {
//        Arrays.fill(MATERIALS, AIR);
        for (Material material : values()) {
//            MATERIALS[material.id] = material;
            MATERIAL_BY_ID_AND_META.put(material.id << 16 | material.metadata, material);
        }
    }

    private static class Builder {
        private final short id;
        private final List<CraftingRecipe> recipes = new ArrayList<>();

        private short metadata = 0;
        private byte maxStack = 64;
        private byte blockId = 0;
        private boolean solid = true;
        private Function<Short, Item> itemCreator;

        public Builder(int id) {
            this.id = (short) id;
        }

        public Builder metadata(int metadata) {
            this.metadata = (short) metadata;
            return this;
        }

        public Builder maxStack(int maxStack) {
            this.maxStack = (byte) maxStack;
            return this;
        }

        public Builder blockId(int blockId) {
            this.blockId = (byte) blockId;
            return this;
        }

        public Builder recipe(CraftingRecipe recipe) {
            recipes.add(recipe);
            return this;
        }

        public Builder itemCreator(Function<Short, Item> itemCreator) {
            this.itemCreator = itemCreator;
            return this;
        }
    }

}

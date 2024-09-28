package domrbeeson.gamma.crafting;

import domrbeeson.gamma.crafting.recipes.*;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Set;

public final class RecipeManager {

    private final Set<CraftingRecipe> recipes = new HashSet<>();

    public RecipeManager() {
        register(new OakPlanksRecipe());
        register(new SprucePlanksRecipe());
        register(new BirchPlanksRecipe());
        register(new DispenserRecipe());
        register(new SandstoneRecipe());
        register(new NoteBlockRecipe());
        register(new PoweredRailsRecipe());
        register(new DetectorRailRecipe());
        register(new StickyPistonRecipe());
        register(new PistonRecipe());
        // TODO wool colour recipes
        register(new GoldBlockRecipe());
        register(new IronBlockRecipe());
        register(new StoneSlabRecipe());
        register(new SandstoneSlabRecipe());
        register(new OakSlabRecipe());
        register(new CobblestoneSlabRecipe());
        register(new BrickBlockRecipe());
        register(new TntRecipe());
        register(new BookshelfRecipe());
        register(new TorchRecipe());
        register(new OakStairsRecipe());
        register(new ChestRecipe());
        register(new DiamondBlockRecipe());
        register(new CraftingTableRecipe());
        register(new FurnaceRecipe());
        register(new OakPressurePlateRecipe()); // TODO doesn't work, not sure why
        register(new LadderRecipe());
        register(new RailRecipe());
        register(new CobblestoneStairsRecipe());
        register(new LeverRecipe());
        register(new StonePressurePlate());
        register(new RedstoneTorchRecipe());
        register(new StoneButtonRecipe());
        register(new SnowBlockRecipe());
        register(new ClayBlockRecipe());
        register(new JukeboxRecipe());
        register(new FenceRecipe());
        register(new JackOLanternRecipe());
        register(new GlowstoneRecipe());
        register(new TrapdoorRecipe());
        register(new ShovelRecipe(Material.IRON_SHOVEL.getItem(), CraftingRecipe.IRON_INGOT));
        register(new PickaxeRecipe(Material.IRON_PICKAXE.getItem(), CraftingRecipe.IRON_INGOT));
        register(new AxeRecipe(Material.IRON_AXE.getItem(), CraftingRecipe.IRON_INGOT));
        register(new FlintAndSteelRecipe());
        register(new BowRecipe());
        register(new ArrowRecipe());
        register(new SwordRecipe(Material.IRON_SWORD.getItem(), CraftingRecipe.IRON_INGOT));
        register(new SwordRecipe(Material.WOOD_SWORD.getItem(), CraftingRecipe.ANY_PLANKS));
        register(new ShovelRecipe(Material.WOOD_SHOVEL.getItem(), CraftingRecipe.ANY_PLANKS));
        register(new PickaxeRecipe(Material.WOOD_PICKAXE.getItem(), CraftingRecipe.ANY_PLANKS));
        register(new AxeRecipe(Material.WOOD_AXE.getItem(), CraftingRecipe.ANY_PLANKS));
        register(new SwordRecipe(Material.STONE_SWORD.getItem(), CraftingRecipe.COBBLESTONE));
        register(new ShovelRecipe(Material.STONE_SHOVEL.getItem(), CraftingRecipe.COBBLESTONE));
        register(new PickaxeRecipe(Material.STONE_PICKAXE.getItem(), CraftingRecipe.COBBLESTONE));
        register(new AxeRecipe(Material.STONE_AXE.getItem(), CraftingRecipe.COBBLESTONE));
        register(new SwordRecipe(Material.DIAMOND_SWORD.getItem(), CraftingRecipe.DIAMOND));
        register(new ShovelRecipe(Material.DIAMOND_SHOVEL.getItem(), CraftingRecipe.DIAMOND));
        register(new PickaxeRecipe(Material.DIAMOND_PICKAXE.getItem(), CraftingRecipe.DIAMOND));
        register(new AxeRecipe(Material.DIAMOND_AXE.getItem(), CraftingRecipe.DIAMOND));
        register(new StickRecipe());
        register(new BowlRecipe());
        register(new MushroomSoupRecipe());
        register(new SwordRecipe(Material.GOLD_SWORD.getItem(), CraftingRecipe.GOLD_INGOT));
        register(new ShovelRecipe(Material.GOLD_SHOVEL.getItem(), CraftingRecipe.GOLD_INGOT));
        register(new PickaxeRecipe(Material.GOLD_PICKAXE.getItem(), CraftingRecipe.GOLD_INGOT));
        register(new AxeRecipe(Material.GOLD_AXE.getItem(), CraftingRecipe.GOLD_INGOT));
        register(new HoeRecipe(Material.WOOD_HOE.getItem(), CraftingRecipe.ANY_PLANKS));
        register(new HoeRecipe(Material.STONE_HOE.getItem(), CraftingRecipe.COBBLESTONE));
        register(new HoeRecipe(Material.IRON_HOE.getItem(), CraftingRecipe.IRON_INGOT));
        register(new HoeRecipe(Material.DIAMOND_HOE.getItem(), CraftingRecipe.DIAMOND));
        register(new HoeRecipe(Material.GOLD_HOE.getItem(), CraftingRecipe.GOLD_INGOT));
        register(new BreadRecipe());
        register(new HelmetRecipe(Material.LEATHER_HELMET.getItem(), CraftingRecipe.LEATHER));
        register(new TunicRecipe(Material.LEATHER_TUNIC.getItem(), CraftingRecipe.LEATHER));
        register(new LeggingsRecipe(Material.LEATHER_LEGS.getItem(), CraftingRecipe.LEATHER));
        register(new BootsRecipe(Material.LEATHER_BOOTS.getItem(), CraftingRecipe.LEATHER));
        register(new HelmetRecipe(Material.CHAIN_HELMET.getItem(), CraftingRecipe.FIRE));
        register(new TunicRecipe(Material.CHAIN_CHEST.getItem(), CraftingRecipe.FIRE));
        register(new LeggingsRecipe(Material.CHAIN_LEGS.getItem(), CraftingRecipe.FIRE));
        register(new BootsRecipe(Material.CHAIN_BOOTS.getItem(), CraftingRecipe.FIRE));
        register(new HelmetRecipe(Material.IRON_HELMET.getItem(), CraftingRecipe.IRON_INGOT));
        register(new TunicRecipe(Material.IRON_CHEST.getItem(), CraftingRecipe.IRON_INGOT));
        register(new LeggingsRecipe(Material.IRON_LEGS.getItem(), CraftingRecipe.IRON_INGOT));
        register(new BootsRecipe(Material.IRON_BOOTS.getItem(), CraftingRecipe.IRON_INGOT));
        register(new HelmetRecipe(Material.DIAMOND_HELMET.getItem(), CraftingRecipe.DIAMOND));
        register(new TunicRecipe(Material.DIAMOND_CHEST.getItem(), CraftingRecipe.DIAMOND));
        register(new LeggingsRecipe(Material.DIAMOND_LEGS.getItem(), CraftingRecipe.DIAMOND));
        register(new BootsRecipe(Material.DIAMOND_BOOTS.getItem(), CraftingRecipe.DIAMOND));
        register(new HelmetRecipe(Material.GOLD_HELMET.getItem(), CraftingRecipe.GOLD_INGOT));
        register(new TunicRecipe(Material.GOLD_CHEST.getItem(), CraftingRecipe.GOLD_INGOT));
        register(new LeggingsRecipe(Material.GOLD_LEGS.getItem(), CraftingRecipe.GOLD_INGOT));
        register(new BootsRecipe(Material.GOLD_BOOTS.getItem(), CraftingRecipe.GOLD_INGOT));
        register(new PaintingRecipe());
        register(new GoldenAppleRecipe());
        register(new SignRecipe());
        register(new OakDoorRecipe());
        register(new BucketRecipe());
        register(new MinecartRecipe());
        register(new IronDoorRecipe());
        register(new BoatRecipe());
        register(new PaperRecipe());
        register(new BookRecipe());
        register(new ChestMinecartRecipe());
        register(new FurnaceMinecartRecipe());
        register(new CompassRecipe());
        register(new FishingRodRecipe());
        register(new ClockRecipe());
        // TODO dyes
        register(new BoneMealRecipe());
        register(new SugarRecipe());
        register(new CakeRecipe());
        register(new BedRecipe());
        register(new RedstoneRepeaterRecipe());
        register(new CookieRecipe());
        register(new ShearsRecipe());
    }

    public void register(CraftingRecipe recipe) {
        recipes.add(recipe);
    }

    public void unregister(CraftingRecipe recipe) {
        recipes.remove(recipe);
    }

    public @Nullable RecipeState checkRecipe(Item[][] grid) {
        // TODO optimise, don't search every single recipe, store recipes against each item so there's less searching
        RecipeState state = null;
        for (CraftingRecipe recipe : recipes) {
            if ((state = recipe.matches(grid)) != null) {
                break;
            }
        }
        return state;
    }

}

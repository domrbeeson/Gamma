package domrbeeson.gamma.crafting;

import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import org.jetbrains.annotations.Nullable;

public interface CraftingRecipe {

    RecipeItem AIR = new RecipeItem(Material.AIR);
    RecipeItem ANY_PLANKS = new RecipeItem(Material.OAK_PLANKS.id);
    RecipeItem ANY_WOOL = new RecipeItem(Material.WHITE_WOOL.id);
    RecipeItem COBBLESTONE = new RecipeItem(Material.COBBLESTONE);
    RecipeItem IRON_INGOT = new RecipeItem(Material.IRON_INGOT);
    RecipeItem STICK = new RecipeItem(Material.STICK);
    RecipeItem STONE = new RecipeItem(Material.STONE);
    RecipeItem DIAMOND = new RecipeItem(Material.DIAMOND);
    RecipeItem GOLD_INGOT = new RecipeItem(Material.GOLD_INGOT);
    RecipeItem LEATHER = new RecipeItem(Material.LEATHER);
    RecipeItem FIRE = new RecipeItem(Material.FIRE);
    RecipeItem STRING = new RecipeItem(Material.STRING);
    RecipeItem WHEAT = new RecipeItem(Material.WHEAT);
    RecipeItem REDSTONE = new RecipeItem(Material.REDSTONE);
    RecipeItem PAPER = new RecipeItem(Material.PAPER);
    RecipeItem OAK_PLANKS = new RecipeItem(Material.OAK_PLANKS);
    RecipeItem SAND = new RecipeItem(Material.SAND);
    RecipeItem FLINT = new RecipeItem(Material.FLINT);
    RecipeItem MINECART = new RecipeItem(Material.MINECART);

    @Nullable RecipeState matches(Item[][] craftingGrid);
    Item getOutput();

}

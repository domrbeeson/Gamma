package domrbeeson.gamma.version;

import domrbeeson.gamma.crafting.CraftingRecipe;
import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.fuel.Fuel;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public record Features(
        int protocol,
        Set<Material> materials,
        Set<EntityType> entities,
        boolean nether,
        boolean leafDecay,
        boolean throwEggs,
        boolean sneaking,
        boolean fishing,
        boolean weather,
        Set<Fuel> fuel,
        Set<CraftingRecipe> recipes,
        boolean tntRequiresFlintAndSteel,
        boolean sheepRequiresShears
) {

    public static class Builder {
        private final int protocol;

        private Set<Material> materials = new HashSet<>();
        private Set<EntityType> entities = new HashSet<>();
        private boolean nether = false;
        private boolean leafDecay = false;
        private boolean throwEggs = false;
        private boolean sneaking = false;
        private boolean fishing = false;
        private boolean weather = false;
        private Set<Fuel> fuel = new HashSet<>();
        private Set<CraftingRecipe> recipes = new HashSet<>();
        private boolean tntRequiresFlintAndSteel = false;
        private boolean sheepRequiresShears = false;
//        private Map<Dimension, Integer> lavaFlowBlocks = new HashMap<>();

        public Builder(int protocol) {
            this.protocol = protocol;
        }

        public Builder extend(MinecraftVersion protocol) {
            Features features = protocol.features;
            materials = features.materials;
            entities = features.entities;
            nether = features.nether;
            leafDecay = features.leafDecay;
            throwEggs = features.throwEggs;
            sneaking = features.sneaking;
            fishing = features.fishing;
            weather = features.weather;
            fuel = features.fuel;
            recipes = features.recipes;
            tntRequiresFlintAndSteel = features.tntRequiresFlintAndSteel;
            sheepRequiresShears = features.sheepRequiresShears;
            return this;
        }

        public Builder material(Material material) {
            materials.add(material);
            return this;
        }

        public Builder materials(Collection<Material> materials) {
            this.materials.addAll(materials);
            return this;
        }

        public Builder entity(EntityType entity) {
            entities.add(entity);
            return this;
        }

        public Builder entities(Collection<EntityType> entities) {
            this.entities.addAll(entities);
            return this;
        }

        public Builder nether() {
            nether = true;
            return this;
        }

        public Builder leafDecay() {
            leafDecay = true;
            return this;
        }

        public Builder throwEggs() {
            throwEggs = true;
            return this;
        }
        
        public Builder sneak() {
            sneaking = true;
            return this;
        }

        public Builder fishing() {
            fishing = true;
            return this;
        }

        public Builder weather() {
            weather = true;
            return this;
        }

        public Builder fuel(Fuel fuel) {
            this.fuel.add(fuel);
            return this;
        }

        public Builder fuel(Collection<Fuel> fuel) {
            this.fuel.addAll(fuel);
            return this;
        }

        public Builder recipe(CraftingRecipe recipe) {
            recipes.add(recipe);
            return this;
        }

        public Builder tntPrimeWithFlintAndSteel() {
            tntRequiresFlintAndSteel = true;
            return this;
        }

        public Builder sheepRequiresShears() {
            sheepRequiresShears = true;
            return this;
        }

        public Features build() {
            return new Features(
                    protocol,
                    materials,
                    entities,
                    nether,
                    leafDecay,
                    throwEggs,
                    sneaking,
                    fishing,
                    weather,
                    fuel,
                    recipes,
                    tntRequiresFlintAndSteel,
                    sheepRequiresShears
            );
        }
    }

}

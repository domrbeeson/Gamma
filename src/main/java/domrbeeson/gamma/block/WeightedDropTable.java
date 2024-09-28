package domrbeeson.gamma.block;

import domrbeeson.gamma.item.Material;
import org.jetbrains.annotations.Nullable;

import java.util.NavigableMap;
import java.util.TreeMap;
import java.util.concurrent.ThreadLocalRandom;

public class WeightedDropTable {

    private final NavigableMap<Double, Material> dropTable = new TreeMap<>();

    private double max = 0;

    public WeightedDropTable add(Material material, double weight) {
        if (weight <= 0) {
            return this;
        }
        dropTable.put(weight, material);
        if (max < weight) {
            max = weight;
        }
        return this;
    }

    public WeightedDropTable nothing(double weight) {
        return add(Material.AIR, weight);
    }

    @Nullable
    public Material next() {
        double random = ThreadLocalRandom.current().nextDouble(0, max);
        Material material = dropTable.higherEntry(random).getValue();
        if (material == Material.AIR) {
            return null;
        }
        return material;
    }

}

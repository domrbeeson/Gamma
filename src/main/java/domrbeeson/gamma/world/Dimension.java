package domrbeeson.gamma.world;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum Dimension {
    OVERWORLD(0),
    NETHER(-1)
    ;

    private static final Map<Integer, Dimension> DIMENSIONS = new HashMap<>();

    public final byte id;

    Dimension(int id) {
        this.id = (byte) id;
    }

    @Nullable
    public static Dimension getById(int id) {
        return DIMENSIONS.get(id);
    }

    static {
        for (Dimension dimension : values()) {
            DIMENSIONS.put((int) dimension.id, dimension);
        }
    }
}

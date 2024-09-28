package domrbeeson.gamma.player;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public enum PlayerAnimation {
    NONE(0),
    SWING_ARM(1),
    DAMAGE(2),
    LEAVE_BED(3),
    CROUCH(104),
    UNCROUCH(105),
    ;

    private static final Map<Byte, PlayerAnimation> ANIMATIONS = new HashMap<>();

    public final byte id;

    PlayerAnimation(int id) {
        this.id = (byte) id;
    }

    @Nullable
    public static PlayerAnimation getById(byte id) {
        return ANIMATIONS.get(id);
    }

    static {
        for (PlayerAnimation a : values()) {
            ANIMATIONS.put(a.id, a);
        }
    }
}

package domrbeeson.gamma.world;

import domrbeeson.gamma.entity.Pos;

public enum Direction {

    DOWN(0, -1, 0),
    UP(0, 1, 0),
    SOUTH(0, 0, -1),
    NORTH(0, 0, 1),
    EAST(-1, 0, 0),
    WEST(1, 0, 0),
    NONE(0, 0, 0),
    ;

    private final int xOffset, yOffset, zOffset;

    private static final Direction[] DIRECTIONS;

    Direction(int xOffset, int yOffset, int zOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        this.zOffset = zOffset;
    }

    static {
        DIRECTIONS = new Direction[values().length];
        for (Direction dir : values()) {
            DIRECTIONS[dir.ordinal()] = dir;
        }

    }

    public static Direction getById(int id) {
        return DIRECTIONS[id];
    }

    public static boolean isInRange(int id) {
        return id > 0 && id <= 5;
    }

    public Pos applyDirection(int x, int y, int z) {
        return new Pos(x + xOffset, y + yOffset, z + zOffset);
    }

    public Pos applyDirection(Pos pos) {
        return applyDirection(pos.getBlockX(), pos.getBlockY(), pos.getBlockZ());
    }

    // TODO methods for getting which direction a position is facing

}

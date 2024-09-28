package domrbeeson.gamma.entity;

public record Pos(double x, double y, double z, float yaw, float pitch) {

    public static final Pos ZERO = new Pos(0, 0, 0);

    public Pos() {
        this(0, 0, 0, 0, 0);
    }

    public Pos(double x, double y, double z) {
        this(x, y, z, 0, 0);
    }

    public Pos add(Pos pos) {
        return new Pos(x + pos.x, y + pos.y, z + pos.z, pos.yaw, pos.pitch);
    }

    public Pos add(double x, double y, double z) {
        return add(new Pos(x, y, z));
    }

    public Pos add(float yaw, float pitch) {
        return add(new Pos(x, y, z, this.yaw + yaw, this.pitch + pitch));
    }

    public Pos add(int x, int y, int z) {
        return add(new Pos(x, y, z));
    }

    public Pos difference(Pos pos) {
        return new Pos(pos.x - x, pos.y - y, pos.z - z, pos.yaw - yaw, pos.pitch - pitch);
    }

    // https://minecraft.wiki/w/Distance
    public double distance(Pos pos) {
        return Math.sqrt(Math.pow(x - pos.x, 2) + Math.pow(y - pos.y, 2) + Math.pow(z - pos.z, 2));
    }

    // https://minecraft.wiki/w/Distance#Taxicab_distance
    public double taxicabDistance(Pos pos) {
        return (x - pos.x) + (y - pos.y) + (z - pos.z);
    }

    // https://minecraft.wiki/w/Distance#Chebyshev_distance
    public double chebyshevDistance(Pos pos) {
        return Math.max(Math.max(x - pos.x, y - pos.y), z - pos.z);
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + "]";
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pos pos)) {
            return false;
        }
        return pos.x == x
                && pos.y == y
                && pos.z == z
                && pos.yaw == yaw
                && pos.pitch == pitch;
    }

    public int getBlockX() {
        return (int) Math.floor(x);
    }

    public short getBlockY() {
        return (short) Math.floor(y);
    }

    public int getBlockZ() {
        return (int) Math.floor(z);
    }

    public int getChunkX() {
        return (int) x >> 4;
    }

    public int getChunkZ() {
        return (int) z >> 4;
    }

    public record EncodedPos(int x, int y, int z, byte yaw, byte pitch) {
        @Override
        public String toString() {
            return "[" + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch + "]";
        }
    }

    public EncodedPos encode() {
        return new EncodedPos(
                encodeDoubletoInt(x),
                encodeDoubletoInt(y),
                encodeDoubletoInt(z),
                encodeFloatToByte(yaw),
                encodeFloatToByte(pitch)
        );
    }

    public static int encodeDoubletoInt(double coord) {
        return (int) Math.floor(coord * 32d);
    }

    public static byte encodeFloatToByte(float look) {
        return (byte) (look * 256f / 360f);
    }

}
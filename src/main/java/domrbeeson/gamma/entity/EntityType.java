package domrbeeson.gamma.entity;

public enum EntityType {
    PLAYER(0),

    CREEPER(50),
    SKELETON(51),
    SPIDER(52),
    GIANT_ZOMBIE(53),
    ZOMBIE(54),
    SLIME(55),
    GHAST(56),
    ZOMBIE_PIGMAN(57),
    PIG(90),
    SHEEP(91),
    COW(92),
    CHICKEN(93),
    SQUID(94),
    WOLF(95),

    ITEM(1),
    PAINTING(9),

    BOAT(1),
    MINECART(10),
    CHEST_MINECART(11),
    FURNACE_MINECART(12),
    PRIMED_TNT(50),
    ARROW(60),
    SNOWBALL(61),
    EGG(62),
    FIREBALL(63),
    SAND(70),
    GRAVEL(71),
    FISHING_FLOAT(90),
    ;

    public final byte id;

    EntityType(int id) {
        this.id = (byte) id;
    }

}

package domrbeeson.gamma.inventory;

public enum InventoryType {
    PLAYER(-1, 45),
    CHEST_1_ROW(0, 9),
    CHEST_2_ROWS(0, 18),
    CHEST_3_ROWS(0, 27),
    CHEST_4_ROWS(0, 36),
    CHEST_5_ROWS(0, 45),
    CHEST_6_ROWS(0, 54),
    CRAFTING_TABLE(1, 10),
    FURNACE(2, 3),
    DISPENSER(3, 9),
    ;

    public final byte id, slots;

    InventoryType(int id, int slots) {
        this.id = (byte) id;
        this.slots = (byte) slots;
    }

}

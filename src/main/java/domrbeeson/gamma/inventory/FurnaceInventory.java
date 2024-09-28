package domrbeeson.gamma.inventory;

import domrbeeson.gamma.fuel.Fuel;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.item.SmeltableItem;
import domrbeeson.gamma.network.packet.out.WindowProgressBarPacketOut;
import org.jetbrains.annotations.Nullable;

public class FurnaceInventory extends ProgressBarInventory {

    private static final short SMELT_TICKS = 200;
    private static final short INPUT_SLOT = 0;
    private static final short FUEL_SLOT = 1;
    private static final short OUTPUT_SLOT = 2;

    private short progress = 0;
    private short fuelBurnTicks = 0;

    public FurnaceInventory() {
        super(InventoryType.FURNACE, "");
    }

    public void setInput(Item item) {
        setSlot(INPUT_SLOT, item);
    }

    public Item getInput() {
        return getSlot(0);
    }

    public void clearInput() {
        setInput(Item.AIR);
    }

    public void setFuel(Item item) {
        setSlot(FUEL_SLOT, item);
    }

    public Item getFuel() {
        return getSlot(FUEL_SLOT);
    }

    public void clearFuel() {
        setFuel(Item.AIR);
    }

    public void setOutput(Item output) {
        setSlot(OUTPUT_SLOT, output);
    }

    public Item getOutput() {
        return getSlot(OUTPUT_SLOT);
    }

    public void clearOutput() {
        setOutput(Item.AIR);
    }

    public short getProgress(short max) {
        return (short) Math.floor((double) max / SMELT_TICKS * progress);
    }

    @Override
    public boolean setSlot(int slot, @Nullable Item item, boolean update) {
        boolean updated = super.setSlot(slot, item, update);
        if (slot == FUEL_SLOT) {
            fuelBurnTicks = 0;
        }
        return updated;
    }

    @Override
    public void tick(long ticks) {
        super.tick(ticks);

        // TODO when smelting starts, remove 1 fuel and count down the burn ticks until 0
        // TODO don't know why the fire/arrow doesn't progress on the client

//        if (fuelBurnTicks >= fuel.getBurnTicks()) {
//            decreaseFuel(fuel, input);
//        }

        if (!(getInput() instanceof SmeltableItem)) {
            return;
        }
        SmeltableItem input = (SmeltableItem) getInput();

        Item output = getOutput();
        if (output.id() != 0 && output.amount() >= Material.get(output.id(), output.metadata()).maxStack) {
            return;
        }

        Fuel fuel = Fuel.get(getFuel().id());
        if (fuel == null) {
            progress = 0;
            return;
        }

        // TODO furnace burn event?
        progress++;
        if (progress >= SMELT_TICKS) {
            progress = 0;
            setOutput(input.getSmeltingOutput().getItem());

            // TODO set furnace block to unlit furnace
            // TODO furnace complete event?
        } else {
            // TODO set furnace block to lit furnace
            // TODO furnace start event?
        }

        if (!getViewers().isEmpty()) {
            WindowProgressBarPacketOut arrowPacket = new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_ARROW);
            WindowProgressBarPacketOut firePacket = new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_FIRE);
            getViewers().forEach(viewer -> {
                viewer.sendPacket(arrowPacket);
                viewer.sendPacket(firePacket);
            });
        }
    }

    private int decreaseFuel(Fuel fuel, SmeltableItem input) {
        byte newFuelAmount = (byte) (input.amount() - 1);
        if (newFuelAmount <= 0) {
            setInput(fuel.getItemAfterSmelting());
        } else {
            setInput(Material.get(input.id(), input.metadata()).getItem(newFuelAmount));
        }
        return newFuelAmount;
    }

}

package domrbeeson.gamma.inventory;

import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.network.packet.out.WindowProgressBarPacketOut;
import domrbeeson.gamma.player.Player;

public class FurnaceInventory extends Inventory {

    private static final short SMELT_TICKS = 200;
    private static final short INPUT_SLOT = 0;
    private static final short FUEL_SLOT = 1;
    private static final short OUTPUT_SLOT = 2;

    private short cookProgress = 0;
    private short previousCookProgress = 0;
    private short totalFuelTicks = 0;
    private short fuelTicksRemaining = 0;
    private short previousFuelTicksRemaining = 0;

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
        setInput(Item.getAir());
    }

    public void setFuel(Item item) {
        setSlot(FUEL_SLOT, item);
    }

    public Item getFuel() {
        return getSlot(FUEL_SLOT);
    }

    public void clearFuel() {
        setFuel(Item.getAir());
    }

    public void setOutput(Item output) {
        setSlot(OUTPUT_SLOT, output);
    }

    public Item getOutput() {
        return getSlot(OUTPUT_SLOT);
    }

    public void clearOutput() {
        setOutput(Item.getAir());
    }

    public boolean isBurning() {
        return fuelTicksRemaining > 0;
    }

    public short getCookProgress() {
        return cookProgress;
    }

    public short getFuelBurnProgress() {
        return fuelTicksRemaining;
    }

    @Override
    public boolean canDepositIntoSlot(int slot, Item item) {
        if (slot == FUEL_SLOT) {
            return item.getItemHandler().isFuel();
        }
        return slot != OUTPUT_SLOT;
    }

    @Override
    public void onOpen(Player player) {
        player.sendPacket(new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_ARROW, getCookProgress()));
        player.sendPacket(new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_FIRE, getFuelBurnProgress()));
        player.sendPacket(new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_TOTAL_FUEL, totalFuelTicks));
    }

    @Override
    public void tick(long ticks) {
        super.tick(ticks);

        // TODO don't know why the fire/arrow doesn't progress on the client

        if (fuelTicksRemaining > 0) {
            if (getInput().getItemHandler().isSmeltable() && getOutput().getAmount() < getOutput().getMaterial().maxStack) {
                cookProgress++;
                if (cookProgress >= SMELT_TICKS) {
                    getInput().setAmount(getInput().getAmount() - 1);
//                    setInput(input.getMaterial().getItem(input.getAmount() - 1));
                    setOutput(new Item(getInput().getItemHandler().getSmeltingOutput(), getOutput().getAmount() + 1));
                    cookProgress = 0;
                }
            } else {
                cookProgress = 0;
            }

            fuelTicksRemaining--;
        }

        if (fuelTicksRemaining == 0) {
            if (getFuel().getItemHandler().isFuel()
                    && getInput().getItemHandler().isSmeltable()
                    && (getOutput().getMaterial() == Material.AIR || getOutput().getAmount() < getOutput().getMaterial().maxStack)) {

                getFuel().setAmount(getFuel().getAmount() - 1);
//                setFuel(getFuel().getMaterial().getItem(getFuel().getAmount() - 1));
                fuelTicksRemaining = getFuel().getItemHandler().getFuelTicks();
                totalFuelTicks = fuelTicksRemaining;

                if (hasViewers()) {
                    WindowProgressBarPacketOut totalFuelPacket = new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_TOTAL_FUEL, totalFuelTicks);
                    getViewers().forEach(viewer -> {
                        viewer.sendPacket(totalFuelPacket);
                    });
                }
            }
        }

        if (cookProgress != previousCookProgress) {
            previousCookProgress = cookProgress;
            if (hasViewers()) {
                WindowProgressBarPacketOut arrowPacket = new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_ARROW, getCookProgress());
                getViewers().forEach(viewer -> {
                    viewer.sendPacket(arrowPacket);
                });
            }
        }

        if (fuelTicksRemaining != previousFuelTicksRemaining) {
            previousFuelTicksRemaining = fuelTicksRemaining;
            if (hasViewers()) {
                WindowProgressBarPacketOut firePacket = new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_FIRE, getFuelBurnProgress());
                getViewers().forEach(viewer -> {
                    viewer.sendPacket(firePacket);
                });
            }
        }
    }
}

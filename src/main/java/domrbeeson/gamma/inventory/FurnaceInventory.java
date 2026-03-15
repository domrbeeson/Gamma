package domrbeeson.gamma.inventory;

import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.network.packet.PacketOut;
import domrbeeson.gamma.network.packet.out.WindowProgressBarPacketOut;
import domrbeeson.gamma.network.packet.out.WindowSlotPacketOut;
import domrbeeson.gamma.player.Player;

public class FurnaceInventory extends Inventory {

    private static final short SMELT_TICKS = 200;
    private static final short INPUT_SLOT = 0;
    private static final short FUEL_SLOT = 1;
    private static final short OUTPUT_SLOT = 2;

    private State state = State.IDLE;
    private short burnProgressTicks = 0;
    private boolean burnProgressChanged = false;
    private short totalFuelTicks = 0;
    private short remainingFuelTicks = 0;
    private boolean remainingFuelChanged = false;
    private boolean totalFuelChanged = false;
    private Material burningMaterial = Material.AIR;

    public FurnaceInventory() {
        super(InventoryType.FURNACE, "");
    }

    public void setInput(Item item) {
        setSlot(INPUT_SLOT, item);
    }

    public Item getInput() {
        return getSlot(INPUT_SLOT);
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

    public boolean isBurning() {
        return state == State.BURNING;
    }

    public short getBurnProgress() {
        return burnProgressTicks;
    }

    public short getRemainingFuelTicks() {
        return remainingFuelTicks;
    }

    @Override
    public boolean canDepositIntoSlot(int slot, Item item) {
        if (slot == FUEL_SLOT) {
            return item.getMaterial().isFuel();
        }
        return slot != OUTPUT_SLOT;
    }

    @Override
    public void onOpen(Player player) {
        player.sendPacket(new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_ARROW, burnProgressTicks));
        player.sendPacket(new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_FIRE, remainingFuelTicks));
        player.sendPacket(new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_TOTAL_FUEL, totalFuelTicks));
    }

    @Override
    public void tick(long ticks) {
        super.tick(ticks);

        if (burnProgressChanged) {
            burnProgressChanged = false;
            PacketOut packet = new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_ARROW, burnProgressTicks);
            getViewers().forEach(viewer -> viewer.sendPacket(packet));
        }

        if (totalFuelChanged) {
            totalFuelChanged = false;
            PacketOut packet = new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_TOTAL_FUEL, totalFuelTicks);
            getViewers().forEach(viewer -> viewer.sendPacket(packet));
        }

        if (remainingFuelChanged) {
            remainingFuelChanged = false;
            PacketOut packet = new WindowProgressBarPacketOut(this, WindowProgressBarPacketOut.Action.FURNACE_FIRE, remainingFuelTicks);
            getViewers().forEach(viewer -> viewer.sendPacket(packet));
        }

        if (state == State.BURNING) {
            incrementBurnProgress();

            Item input = getInput();
            if (this.burningMaterial != input.getMaterial()) {
                resetBurnProgress();
                this.burningMaterial = input.getMaterial();
            } else if (!this.burningMaterial.isSmeltable()) {
                resetBurnProgress();
            } else if (getOutput().amount() >= getOutput().getMaterial().maxStack) {
                resetBurnProgress();
            } else if (burnProgressTicks >= SMELT_TICKS) {
                resetBurnProgress();
                setInput(input.addAmount(-1));
                setOutput(new Item(this.burningMaterial.smeltingOutput, getOutput().amount() + 1));
                getViewers().forEach(viewer -> viewer.sendPacket(new WindowSlotPacketOut(getType(), OUTPUT_SLOT, getOutput())));
            }

            decrementRemainingFuelTicks();
            if (remainingFuelTicks <= 0) {
                state = State.IDLE;
            }
        }

         if (state == State.IDLE) {
             Item fuel = getFuel();
             if (!fuel.getMaterial().isFuel()) {
                 resetBurnProgress();
                 return;
             }
             Item input = getInput();
             if (!input.getMaterial().isSmeltable()) {
                 resetBurnProgress();
                 return;
             }

             state = State.BURNING;
             this.burningMaterial = input.getMaterial();
             setRemainingFuelTicks(fuel.getMaterial());
             resetBurnProgress();

             setFuel(fuel.addAmount(-1));
         }
    }

    private void setRemainingFuelTicks(Material material) {
        totalFuelTicks = material.burnTicks;
        remainingFuelTicks = totalFuelTicks;
        remainingFuelChanged = true;
        totalFuelChanged = true;
    }

    private void decrementRemainingFuelTicks() {
        remainingFuelTicks--;
        remainingFuelChanged = true;
    }

    private void incrementBurnProgress() {
        burnProgressTicks++;
        burnProgressChanged = true;
    }

    private void resetBurnProgress() {
        if (burnProgressTicks != 0) {
            burnProgressChanged = true;
        }
        burnProgressTicks = 0;
    }

    private enum State {
        IDLE,
        BURNING,
        ;
    }

}

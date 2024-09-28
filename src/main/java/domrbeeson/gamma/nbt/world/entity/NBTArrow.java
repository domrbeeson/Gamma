package domrbeeson.gamma.nbt.world.entity;

import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.entity.Entity;
import domrbeeson.gamma.entity.object.ArrowEntity;
import domrbeeson.gamma.nbt.NBTTag;
import domrbeeson.gamma.nbt.tags.NBTByte;
import domrbeeson.gamma.nbt.tags.NBTCompound;
import domrbeeson.gamma.player.Player;

import java.util.Map;

public class NBTArrow extends NBTProjectile {

    private final byte inBlockMetadata;
    private final boolean playerShooter;

    public NBTArrow(NBTCompound compound) {
        super(compound);
        inBlockMetadata = compound.getByte("inData").getValue();
        playerShooter = compound.getByte("player").getValue() == 1;
    }

    public NBTArrow(Entity<?> entity) {
        super("Arrow", entity);
        Block block = entity.getWorld().getBlock(getBlockX(), getBlockY(), getBlockZ());
        inBlockMetadata = block.metadata();

        if (entity instanceof ArrowEntity arrow) {
            playerShooter = arrow.getShooter() instanceof Player;
        } else {
            playerShooter = false;
        }
    }

    @Override
    public Map<String, NBTTag> createCompoundTags() {
        Map<String, NBTTag> tags = super.createCompoundTags();

        tags.put("inData", new NBTByte(inBlockMetadata));
        tags.put("player", new NBTByte(playerShooter));

        return tags;
    }
}

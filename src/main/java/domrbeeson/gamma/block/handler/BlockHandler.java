package domrbeeson.gamma.block.handler;

import domrbeeson.gamma.MinecraftServer;
import domrbeeson.gamma.block.Block;
import domrbeeson.gamma.event.events.block.BlockChangeEvent;
import domrbeeson.gamma.item.Item;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.Chunk;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface BlockHandler {

    default void onBreak(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata) {

    }

    default void onPlace(MinecraftServer server, BlockChangeEvent event, @Nullable Player player) {

    }

    default void onLeftClick(MinecraftServer server, Block block, Player player) {

    }

    default void randomTick(MinecraftServer server, Chunk chunk, int x, int y, int z, byte id, byte metadata, long tick) {

    }

    default void onContactWithWater(MinecraftServer server, Block block) {

    }

    default void onContactWithLava(MinecraftServer server, Block block) {

    }

    default void onDirectPower(MinecraftServer server, Block block, byte power) {

    }

    default void onIndirectPower(MinecraftServer server, Block block, byte power) {

    }

    // This is used to stop the tile entity being wiped when furnaces start burning
    default boolean triggerBreakAndPlaceOnBlockChange(MinecraftServer server, byte oldId, byte oldMeta, byte newId, byte newMeta) {
        return true;
    }

    // TODO onWalk for farmland and pressure plates?

    default List<Item> getDrops(MinecraftServer server, Chunk chunk, int x, int y, int z, byte blockId, byte blockMetadata, short toolId) {
        return List.of();
    }

    default boolean onRightClick(MinecraftServer server, Block block, Player player) {
        return false;
    }

    default boolean update(MinecraftServer server, Block block, long tick) {
        return false;
    }


    default boolean canPlace(Chunk chunk, int x, int y, int z) {
        return true;
    }

    default boolean isSolid() {
        return true;
    }

    default boolean isLiquid() {
        return false;
    }

    default boolean isPermeable() {
        return false;
    }

    default boolean isTransparent() {
        return false;
    }

    default byte getEmittedLight() {
        return 0;
    }

    default boolean canPower() {
        return false;
    }

}

package domrbeeson.gamma.item.handlers;

import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.event.events.player.PlayerRightClickBlockEvent;
import domrbeeson.gamma.item.ItemHandler;
import domrbeeson.gamma.item.Material;
import domrbeeson.gamma.world.World;

public class FlintAndSteelItemHandler implements ItemHandler {

    private static final int MIN_PORTAL_WIDTH = 2;
    private static final int MIN_PORTAL_HEIGHT = 3;
    private static final int MAX_PORTAL_WIDTH = 2; // TODO make configurable
    private static final int MAX_PORTAL_HEIGHT = 3; // TODO make configurable

    @Override
    public boolean use(PlayerRightClickBlockEvent event) {
        event.getHeldItem().setMetadata((short)(event.getHeldItem().metadata() + 1));

        if (createNetherPortal(event, true) || createNetherPortal(event, false)) {
            return true;
        }

        event.getPlayer().getWorld().setBlock(event.getDirection().applyDirection(event.getX(), event.getY(), event.getZ()), Material.FIRE);
        return true;
    }

    private boolean createNetherPortal(PlayerRightClickBlockEvent event, boolean isX) {
        int x = event.getX();
        int y = event.getY();
        int z = event.getZ();

        int width = 0;
        int height;

        byte check = 0; // 0 = positive x/z, 1 = upwards, 2 = negative x/z, 3 = downwards
        boolean portalFound = false;
        World world = event.getPlayer().getWorld();
        Pos bottomRightCorner = Pos.ZERO;
        Pos topRightCorner = Pos.ZERO;
        Pos bottomLeftCorner = Pos.ZERO;
        byte addX;
        byte addZ;
        if (isX) {
            addX = 1;
            addZ = 0;
        } else {
            addX = 0;
            addZ = 1;
        }
        IO.println("addX: " + addX + ", addZ: " + addZ);
        while (!portalFound) {
            switch (check) {
                case 0:
                    if (world.getMaterial(x + addX, y, z + addZ) == Material.OBSIDIAN) {
                        x += addX;
                        z += addZ;
                        width++;
                        if (width > MAX_PORTAL_WIDTH) {
                            return false;
                        }
                    } else if (world.getMaterial(x, y + 1, z) == Material.OBSIDIAN) {
                        bottomRightCorner = new Pos(x - addX, y + 1, z - addZ);
                        y++;
                        check = 1;
                    } else if (world.getMaterial(x + addX, y + 1, z + addZ) == Material.OBSIDIAN) {
                        bottomRightCorner = new Pos(x, y + 1, z);
                        x += addX;
                        y++;
                        z += addZ;
                        check = 1;
                    } else {
                        return false; // Z fails here
                    }
                    break;
                case 1:
                    if (world.getMaterial(x, y + 1, z) == Material.OBSIDIAN) {
                        y++;
                    } else if (world.getMaterial(x - addX, y, z - addZ) == Material.OBSIDIAN) {
                        topRightCorner = new Pos(x - addX, y - 1, z - addZ);
                        height = topRightCorner.getBlockY() - bottomRightCorner.getBlockY() + 1;
                        if (!isPortalHeightValid(height)) {
                            return false;
                        }
                        x -= addX;
                        z -= addZ;
                        check = 2;
                        width = 0;
                    } else if (world.getMaterial(x - addX, y + 1, z - addZ) == Material.OBSIDIAN) {
                        topRightCorner = new Pos(x - addX, y, z - addZ);
                        height = topRightCorner.getBlockY() - bottomRightCorner.getBlockY() + 1;
                        if (!isPortalHeightValid(height)) {
                            return false;
                        }
                        x -= addX;
                        y++;
                        z -= addZ;
                        check = 2;
                        width = 0;
                    } else {
                        return false;
                    }
                    break;
                case 2:
                    if (world.getMaterial(x - addX, y, z - addZ) == Material.OBSIDIAN) {
                        x -= addX;
                        z -= addZ;
                        width++;
                        if (width > MAX_PORTAL_WIDTH) {
                            return false;
                        }
                    } else if (world.getMaterial(x, y - 1, z) == Material.OBSIDIAN) {
                        if (!isPortalWidthValid((width))) {
                            return false;
                        }
                        y--;
                        check = 3;
                    } else if (world.getMaterial(x - addX, y - 1, z - addZ) == Material.OBSIDIAN) {
                        if (!isPortalWidthValid((width))) {
                            return false;
                        }
                        x -= addX;
                        y--;
                        z -= addZ;
                        check = 3;
                    } else {
                        return false;
                    }
                    break;
                case 3:
                    if (world.getMaterial(x, y - 1, z) == Material.OBSIDIAN) {
                        y--;
                    } else if (world.getMaterial(x + addX, y, z + addZ) == Material.OBSIDIAN) {
                        bottomLeftCorner = new Pos(x + addX, y + 1, z + addZ);
                        x += addX;
                        z += addZ;
                        check = 0;
                        width = 0;
                    } else if (world.getMaterial(x + addX, y - 1, z + addZ) == Material.OBSIDIAN) {
                        bottomLeftCorner = new Pos(x + addX, y, z + addZ);
                        x += addX;
                        y--;
                        z += addZ;
                        check = 0;
                        width = 0;
                    } else {
                        return false;
                    }
                    break;
            }
            portalFound = x == event.getX() && y == event.getY() && z == event.getZ();
        }

        for (int ix = bottomLeftCorner.getBlockX(); ix <= bottomRightCorner.getBlockX(); ix++) {
            for (int iy = bottomRightCorner.getBlockY(); iy <= topRightCorner.getBlockY(); iy++) {
                for (int iz = bottomLeftCorner.getBlockZ(); iz <= bottomRightCorner.getBlockZ(); iz++) {
                    world.setBlock(ix, iy, iz, Material.PORTAL);
                }
            }
        }

        return true;
    }

    private boolean isPortalWidthValid(int width) {
        System.out.println("width: " + width + ", min: " + MIN_PORTAL_WIDTH + ", max: " + MAX_PORTAL_WIDTH);
        return width >= MIN_PORTAL_WIDTH && width <= MAX_PORTAL_WIDTH;
    }

    private boolean isPortalHeightValid(int height) {
        System.out.println("height: " + height + ", min: " + MIN_PORTAL_HEIGHT + ", max: " + MAX_PORTAL_HEIGHT);
        return height >= MIN_PORTAL_HEIGHT && height <= MAX_PORTAL_HEIGHT;
    }

}

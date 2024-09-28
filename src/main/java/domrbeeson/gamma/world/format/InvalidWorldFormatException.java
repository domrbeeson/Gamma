package domrbeeson.gamma.world.format;

import domrbeeson.gamma.world.World;

public class InvalidWorldFormatException extends Exception {

    public InvalidWorldFormatException(World world) {
        super("World '" + world.getName() + "' is not format '" + world.getFormat().getClass().getSimpleName() + "'!");
    }

}

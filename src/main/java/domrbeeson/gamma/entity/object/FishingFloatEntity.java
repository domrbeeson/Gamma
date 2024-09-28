package domrbeeson.gamma.entity.object;

import domrbeeson.gamma.entity.EntityType;
import domrbeeson.gamma.entity.Pos;
import domrbeeson.gamma.player.Player;
import domrbeeson.gamma.world.World;

public class FishingFloatEntity extends ObjectEntity {

    private final Player shooter;

    public FishingFloatEntity(World world, Pos pos, Player shooter) {
        super(EntityType.FISHING_FLOAT, world, pos, EMPTY_COLLISION_BOX);
        this.shooter = shooter;
    }

    public Player getShooter() {
        return shooter;
    }

}

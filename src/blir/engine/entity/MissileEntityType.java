package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;

import java.awt.Color;
import java.util.HashMap;

/**
 *
 * @author Blir
 */
public class MissileEntityType extends EntityType {

    public MissileEntityType(int id) {
        super(id, "Missile", Color.BLUE);
    }

    @Override
    public void init(SinglePlayerGame game) {
    }

    @Override
    public void onSpawnTick(SinglePlayerGame game) {
    }
}

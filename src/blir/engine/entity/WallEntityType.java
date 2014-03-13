package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import java.awt.Color;

/**
 *
 * @author Blir
 */
public class WallEntityType extends EntityType {

    public WallEntityType(int id) {
        super(id, "Wall", Color.ORANGE);
    }

    @Override
    public void init(SinglePlayerGame game) {
    }

    @Override
    public void onSpawnTick(SinglePlayerGame game) {
    }

    @Override
    public Entity spawn() {
        return new Wall();
    }
}

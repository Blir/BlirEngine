package blir.engine.entity;

import blir.engine.game.Game;
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
    public void init(Game game) {
    }

    @Override
    public void onSpawnTick(Game game) {
    }

    @Override
    public Entity spawn() {
        return new Wall();
    }
}

package blir.engine.entity;

import static blir.engine.entity.EntityType.wall;
import blir.engine.game.Game;

/**
 *
 * @author Blir
 */
public class Wall extends MortalEntity {

    public Wall() {
        super(wall.id, 150);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
    }
}

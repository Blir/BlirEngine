package blir.engine.entity;

import blir.engine.game.Game;

import static blir.engine.entity.EntityType.wall;

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

package blir.engine.entity;

import blir.engine.game.Game;

/**
 *
 * @author Blir
 */
public class GenericEntity extends Entity {

    public GenericEntity(int id) {
        super(id);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
    }
}

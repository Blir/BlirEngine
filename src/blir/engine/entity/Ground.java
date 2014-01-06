package blir.engine.entity;

import blir.engine.game.Game;
import java.awt.Color;

/**
 *
 * @author Blir
 */
public class Ground extends EntityType {

    public Ground(int id) {
        super(id, "Ground", Color.GREEN);
    }

    @Override
    public void init(Game game) {
    }

    @Override
    public void entityInit(Entity entity) {
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
    }

    @Override
    public void onSpawnTick(Game game) {
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
    }
}

package blir.engine.entity;

import blir.engine.game.Game;

import java.awt.Color;
import java.util.HashMap;

/**
 *
 * @author Blir
 */
public class Missile extends CombatEntityType {

    public Missile(int id) {
        super(id, "Missile", Color.BLUE, 20);
    }

    @Override
    public void init(Game game) {
        damageMap = new HashMap<>();
        damageMap.put(EntityType.enemy.id, 50);
    }

    @Override
    public void entityInit(Entity entity) {
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        if (damageDealt > 0) {
            game.getEntityAt(x, y).setAlive(false);
        } else {
            
        }
    }

    @Override
    public void onSpawnTick(Game game) {
    }
}

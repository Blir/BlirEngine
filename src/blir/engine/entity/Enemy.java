package blir.engine.entity;

import blir.engine.game.Game;

import java.awt.Color;
import java.util.HashMap;

import static blir.engine.entity.EntityType.player;

/**
 *
 * @author Blir
 */
public class Enemy extends CombatEntityType {

    public Enemy(int id) {
        super(id, "Enemy", Color.RED, 100);
    }

    @Override
    public void init(Game game) {
        damageMap = new HashMap<>();
        damageMap.put(player.id, 5);
    }

    @Override
    public void entityInit(Entity entity) {
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
    }

    @Override
    public void onSpawnTick(Game game) {
        if (game.getTick() % 50 == 0) {
            game.spawnEntityAt(player.getPos() + 25, 25, this);
        }
    }
}

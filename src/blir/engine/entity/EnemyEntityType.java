package blir.engine.entity;

import blir.engine.game.Game;

import java.awt.Color;
import java.util.HashMap;

import static blir.engine.entity.EntityType.player;

/**
 *
 * @author Blir
 */
public class EnemyEntityType extends EntityType {

    public EnemyEntityType(int id) {
        super(id, "Enemy", Color.RED);
    }

    @Override
    public void init(Game game) {
    }

    @Override
    public void onSpawnTick(Game game) {
        if (game.getTick() % 50 == 0) {
            game.spawnEntity(player.getPos() + 25, 25, spawn());
        }
    }
}

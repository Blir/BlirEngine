package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import blir.engine.game.Team;

import java.awt.Color;

/**
 *
 * @author Blir
 */
public class WarriorEntityType extends EntityType implements Team {

    int damageDealt;

    public WarriorEntityType(int id, int spawnerID) {
        super(id, "Warrior", Color.RED, new EntitySpawner(spawnerID, "Warrior Spawner", Color.PINK, 25, 10, id));
    }

    @Override
    public Entity spawn() {
        return new Warrior();
    }

    @Override
    public void init(SinglePlayerGame game) {
    }

    @Override
    public void onSpawnTick(SinglePlayerGame game) {
    }

    @Override
    public int getScore() {
        return damageDealt;
    }
}

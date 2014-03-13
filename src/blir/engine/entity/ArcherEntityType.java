package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import blir.engine.game.Team;

import java.awt.Color;

/**
 *
 * @author Blir
 */
public class ArcherEntityType extends EntityType implements Team {

    int damageDealt;
    
    public ArcherEntityType(int id, int spawnerID) {
        super(id, "Archer", Color.YELLOW, new EntitySpawner(spawnerID, "Archer Spawner", Color.GREEN, 25, 10, id));
    }

    @Override
    public Entity spawn() {
        return new Archer();
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

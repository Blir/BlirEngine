package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import blir.engine.game.Team;

import java.awt.Color;

/**
 *
 * @author Blir
 */
public class JuggernautEntityType extends EntityType implements Team {

    int damageDealt;
    int deaths;
    int kills;
    
    public JuggernautEntityType(int id, int spawnID) {
        super(id, "Juggernaut", Color.RED, new EntitySpawner(spawnID, "Juggernaut Spawner", Color.PINK, 25, 8, id));
    }

    @Override
    public void init(SinglePlayerGame game) {
    }

    @Override
    public void onSpawnTick(SinglePlayerGame game) {
    }

    @Override
    public int getScore() {
        return damageDealt + 5 * kills - 15 * deaths;
    }
    
    @Override
    public Entity spawn() {
        return new Juggernaut();
    }
}

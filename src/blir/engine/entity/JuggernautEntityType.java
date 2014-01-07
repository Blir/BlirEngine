package blir.engine.entity;

import blir.engine.game.Game;
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
        super(id, "Juggernaut", Color.RED, new EntitySpawner(spawnID, "Juggernaut Spawner", Color.PINK, 25, 2, id));
    }

    @Override
    public void init(Game game) {
    }

    @Override
    public void onSpawnTick(Game game) {
    }

    @Override
    public int getScore() {
        return damageDealt + 5 * kills - 10 * deaths;
    }
    
    @Override
    public Entity spawn() {
        return new Juggernaut();
    }
}

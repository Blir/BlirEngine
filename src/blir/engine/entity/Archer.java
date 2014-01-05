package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Blir
 */
public class Archer extends CombatEntityType {

    public Archer(int id, int spawnerID) {
        super(id, "Archer", Color.YELLOW, new EntitySpawner(spawnerID, "Archer Spawner", Color.GREEN, 25, 10, id), 20);
    }

    @Override
    public void init() {
        damageMap = new HashMap<>();
        damageMap.put(EntityType.warrior.id, 1);
        damageMap.put(EntityType.wizard.id, 3);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        Location loc = Location.wander(x, y, 1);
        game.moveEntity(x, y, loc.x, loc.y);
    }

    @Override
    public void onSpawnTick(List<Location> entityLocations, Game game) {
    }
}

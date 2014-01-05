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
public class Warrior extends CombatEntityType {

    public Warrior(int id, int spawnerID) {
        super(id, "Warrior", Color.RED, new EntitySpawner(spawnerID, "Warrior Spawner", Color.PINK, 25, 10, id), 20);
    }

    @Override
    public void init() {
        damageMap = new HashMap<>();
        damageMap.put(EntityType.archer.id, 3);
        damageMap.put(EntityType.wizard.id, 1);
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

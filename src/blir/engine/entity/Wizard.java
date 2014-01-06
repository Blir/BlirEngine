package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.HashMap;

/**
 *
 * @author Blir
 */
public class Wizard extends CombatEntityType {

    public Wizard(int id, int spawnerID) {
        super(id, "Wizard", Color.BLUE, new EntitySpawner(spawnerID, "Wizard Spawner", Color.CYAN, 25, 10, id), 20);
    }

    @Override
    public void init(Game game) {
        damageMap = new HashMap<>();
        damageMap.put(EntityType.archer.id, 1);
        damageMap.put(EntityType.warrior.id, 3);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        Location loc = Location.wander(x, y, 1);
        game.moveEntity(x, y, loc.x, loc.y);
    }

    @Override
    public void onSpawnTick(Game game) {
    }

    @Override
    public void entityInit(Entity entity) {
    }
}

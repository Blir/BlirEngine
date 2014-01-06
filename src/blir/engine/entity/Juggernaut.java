package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.HashMap;

/**
 *
 * @author Blir
 */
public class Juggernaut extends CombatEntityType {

    public Juggernaut(int id, int spawnID) {
        super(id, "Juggernaut", Color.RED, new EntitySpawner(spawnID, "Juggernaut Spawner", Color.PINK, 25, 2, id), 275);
    }

    @Override
    public void init(Game game) {
        damageMap = new HashMap<>();
        damageMap.put(zombie.id, 22);
        damageMap.put(wall.id, 22);
    }

    @Override
    public void entityInit(Entity entity) {
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        if (filterByID(game.getSquareNeighbors(x, y, 1), zombie.id).isEmpty()) {
            Location loc = game.getFirstSquareNeighborLocationByID(x, y, 5, zombie.id);
            if (loc == null) {
                if (filterByID(game.getSquareNeighbors(x, y, 2), human.id).isEmpty()) {
                    loc = Location.idleWander(x, y, 1, 50);
                    if (loc != null) {
                        game.moveEntity(x, y, loc.x, loc.y);
                    }
                }
            } else {
                loc = Location.towards(x, y, loc.x, loc.y, 2);
                game.moveEntity(x, y, loc.x, loc.y);
            }
        }
    }

    @Override
    public void onSpawnTick(Game game) {
    }
}

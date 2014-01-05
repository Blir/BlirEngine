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
public class Human extends CombatEntityType {

    public Human(int id) {
        super(id, "Human", Color.PINK, 20);
    }

    @Override
    public void init() {
        damageMap = new HashMap<>();
        damageMap.put(zombie.id, 3);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        Location loc = Location.wander(x, y, 1);
        game.moveEntity(x, y, loc.x, loc.y);
    }

    @Override
    public void onSpawnTick(List<Location> entityLocations, Game game) {
        for (Location loc : entityLocations) {
            List<Location> emptyLocations = game.getEmptyLocations(loc.x, loc.y, 1);
            for (Location empty : emptyLocations) {
                if (filterByID(game.getNeighorSlice(empty.x, empty.y, 1), id).size() == 2
                    && game.getNeighorSlice(empty.x, empty.y, 2).isEmpty()) {

                    game.spawnEntityAt(empty.x, empty.y, id);
                }
            }
        }
    }
}

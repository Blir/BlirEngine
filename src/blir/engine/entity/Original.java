package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.Set;

/**
 *
 * @author Blir
 */
public class Original extends EntityType {

    public Original(int id) {
        super(id, "Original", Color.YELLOW);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        int population = filterByID(game.getSquareNeighbors(x, y, 1), id).size();
        game.getEntityAt(x, y).setAlive(population > 1 && population < 4);
    }

    @Override
    public void onSpawnTick(Game game) {
        Set<Location> entityLocations = game.getEntityLocations(this);
        for (Location loc : entityLocations) {
            Set<Location> emptyLocations = game.getEmptyLocations(loc.x, loc.y, 1);
            for (Location empty : emptyLocations) {
                if (filterByID(game.getSquareNeighbors(empty.x, empty.y, 1), id).size() == 3) {
                   game.spawnEntityAt(empty.x, empty.y, this);
                }
            }
        }
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
    }

    @Override
    public void init() {
    }

    @Override
    public void entityInit(Entity entity) {
    }
}

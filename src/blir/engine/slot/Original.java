package blir.engine.slot;

import blir.engine.game.Game;
import blir.engine.util.Location;
import java.awt.Color;
import java.util.List;

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
        int population = filterByID(game.getNeighbors(x, y, 1), id).size();
        game.getEntityAt(x, y).setAlive(population > 1 && population < 4);
    }

    @Override
    public void onSpawnTick(Game game) {
        //List<Location> location = game.getEmptyLocations(x, y, 1);
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
    }
}

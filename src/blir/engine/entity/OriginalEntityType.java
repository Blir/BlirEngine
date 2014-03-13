package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.Set;

/**
 *
 * @author Blir
 */
public class OriginalEntityType extends EntityType {

    public OriginalEntityType(int id) {
        super(id, "Original", Color.YELLOW);
    }

    @Override
    public void onSpawnTick(SinglePlayerGame game) {
        Set<Location> entityLocations = game.getEntityLocations(this);
        for (Location loc : entityLocations) {
            Set<Location> emptyLocations = game.getEmptyLocations(loc.x, loc.y, 1);
            for (Location empty : emptyLocations) {
                if (filterByID(game.getSquareNeighbors(empty.x, empty.y, 1), id).size() == 3) {
                   game.spawnEntity(empty.x, empty.y, spawn());
                }
            }
        }
    }

    @Override
    public void init(SinglePlayerGame game) {
    }
    
    @Override
    public Entity spawn() {
        return new Original();
    }
}

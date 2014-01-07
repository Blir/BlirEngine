package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.util.List;

import static blir.engine.entity.EntityType.*;

/**
 *
 * @author Blir
 */
public class Juggernaut extends MortalEntity {

    public Juggernaut() {
        super(juggernaut.id, 275);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        Location loc = game.getFirstSquareNeighborLocation(x, y, 5, zombie.id);
        if (loc == null) {
            if (filterByID(game.getSquareNeighbors(x, y, 2), human.id).isEmpty()) {
                loc = Location.idleWander(x, y, 1, 50);
                if (loc != null) {
                    game.moveEntity(x, y, loc);
                }
            }
        } else {
            game.moveEntity(x, y, Location.towards(x, y, loc, 2));
        }
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        List<Entity> neighbors = game.getSquareNeighbors(x, y, 1);
        for (Entity entity : neighbors) {
            if (entity.id == zombie.id) {
                if (((MortalEntity) entity).damage(22)) {
                    juggernaut.damageDealt += 22;
                    juggernaut.kills++;
                    zombie.deaths++;
                }
            } else if (entity.id == wall.id) {
                ((MortalEntity) entity).damage(22);
            }
        }
    }
}

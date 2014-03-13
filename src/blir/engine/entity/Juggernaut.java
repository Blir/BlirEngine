package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import blir.engine.util.Location;

import java.util.List;

import static blir.engine.entity.EntityType.*;
import blir.engine.game.Game;
import blir.engine.swing.Animation;

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
        SinglePlayerGame spg = (SinglePlayerGame) game;
        Location loc = spg.getFirstSquareNeighborLocation(x, y, 5, zombie.id);
        if (loc == null) {
            if (filterByID(spg.getSquareNeighbors(x, y, 2), human.id).isEmpty()) {
                loc = Location.idleWander(x, y, 1, 50);
                if (loc != null) {
                    spg.moveEntity(x, y, loc);
                }
            }
        } else {
            if (!spg.moveEntity(x, y, Location.towards(x, y, loc, 2))) {
                spg.moveEntity(x, y, Location.wander(x, y, 1));
            }
        }
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        SinglePlayerGame spg = (SinglePlayerGame) game;
        List<Entity> neighbors = spg
                .getSquareNeighbors(x, y, 1);
        for (Entity entity : neighbors) {
            if (entity.id == zombie.id) {
                if (((MortalEntity) entity).damage(22)) {
                    spg.addAnimation(Animation.hitAnimationFor(22, entity, spg));
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

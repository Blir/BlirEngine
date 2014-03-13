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
public class Zombie extends MortalEntity {

    public Zombie() {
        super(zombie.id, 27);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        SinglePlayerGame spg = (SinglePlayerGame) game;
        Location loc = spg.getFirstSquareNeighborLocation(x, y, 2, human.id);
        loc = loc == null ? Location.idleWander(x, y, 1, 60) : Location.towards(x, y, loc, 1);
        if (loc != null) {
            spg.moveEntity(x, y, loc);
        }
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        SinglePlayerGame spg = (SinglePlayerGame) game;
        List<Entity> neighbors = spg
                .getSquareNeighbors(x, y, 1);
        for (Entity entity : neighbors) {
            if (entity.id == human.id) {
                MortalEntity mortal = ((MortalEntity) entity);
                if (mortal.damage(4)) {
                    spg.addAnimation(Animation.hitAnimationFor(4, entity, spg));
                    zombie.damageDealt += 4;
                    zombie.kills++;
                    human.deaths++;
                    zombie.toSpawn.add(new Location(entity.x, entity.y));
                }
            } else if (entity.id == wall.id) {
                ((MortalEntity) entity).damage(2);
            } else if (entity.id == juggernaut.id) {
                if (((MortalEntity) entity).damage(3)) {
                    spg.addAnimation(Animation.hitAnimationFor(3, entity, spg));
                    zombie.damageDealt += 3;
                    zombie.kills++;
                    juggernaut.deaths++;
                }
            }
        }
    }
}

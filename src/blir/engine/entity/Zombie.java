package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.util.List;

import static blir.engine.entity.EntityType.*;

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
        Location loc = game.getFirstSquareNeighborLocation(x, y, 2, human.id);
        loc = loc == null ? Location.idleWander(x, y, 1, 60) : Location.towards(x, y, loc, 1);
        if (loc != null) {
            game.moveEntity(x, y, loc);
        }
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        List<Entity> neighbors = game.getSquareNeighbors(x, y, 1);
        for (Entity entity : neighbors) {
            if (entity.id == human.id) {
                MortalEntity mortal = ((MortalEntity) entity);
                mortal.damage(4);
                zombie.damageDealt += 4;
                zombie.toSpawn.add(new Location(entity.x, entity.y));
            } else if (entity.id == wall.id) {
                ((MortalEntity) entity).damage(2);
            } else if (entity.id == juggernaut.id) {
                ((MortalEntity) entity).damage(3);
            }
        }
    }
}

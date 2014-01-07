package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.util.List;

import static blir.engine.entity.EntityType.archer;

/**
 *
 * @author Blir
 */
public class Archer extends MortalEntity {

    public Archer() {
        super(archer.id, 25);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        game.moveEntity(x, y, Location.wander(x, y, 1));
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        List<Entity> neighbors = game.getSquareNeighbors(x, y, 1);
        for (Entity neighbor : neighbors) {
            if (neighbor.id == EntityType.warrior.id) {
                ((MortalEntity) neighbor).damage(1);
                archer.damageDealt++;
            } else if (neighbor.id == EntityType.wizard.id) {
                ((MortalEntity) neighbor).damage(3);
                archer.damageDealt += 3;
            }
        }
    }
}

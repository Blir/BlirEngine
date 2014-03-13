package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import blir.engine.util.Location;

import java.util.List;

import static blir.engine.entity.EntityType.warrior;
import blir.engine.game.Game;

/**
 *
 * @author Blir
 */
public class Warrior extends MortalEntity {

    public Warrior() {
        super(warrior.id, 20);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        ((SinglePlayerGame) game).moveEntity(x, y, Location.wander(x, y, 1));
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        List<Entity> neighbors = ((SinglePlayerGame) game)
                .getSquareNeighbors(x, y, 1);
        for (Entity neighbor : neighbors) {
            if (neighbor.id == EntityType.archer.id) {
                ((MortalEntity) neighbor).damage(3);
                warrior.damageDealt += 3;
            } else if (neighbor.id == EntityType.wizard.id) {
                ((MortalEntity) neighbor).damage(1);
                warrior.damageDealt++;
            }
        }
    }
}

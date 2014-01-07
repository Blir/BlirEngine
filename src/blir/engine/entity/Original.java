package blir.engine.entity;

import blir.engine.game.Game;

/**
 *
 * @author Blir
 */
public class Original extends Entity {

    public Original() {
        super(EntityType.original.id);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        int population = EntityType.filterByID(game.getSquareNeighbors(x, y, 1), id).size();
        game.getEntity(x, y).setAlive(population > 1 && population < 4);
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
    }
}

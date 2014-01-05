package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;
import java.awt.Color;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Blir
 */
public class Zombie extends CombatEntityType {

    public Zombie(int id) {
        super(id, "Zombie", Color.GREEN, 27);
    }

    @Override
    public void init() {
        damageMap = new HashMap<>();
        damageMap.put(human.id, 4);
        damageMap.put(wall.id, 2);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        Location loc = Location.idleWander(x, y, 1, 60);
        if (loc != null) {
            game.moveEntity(x, y, loc.x, loc.y);
        }
    }

    @Override
    public void onSpawnTick(Game game) {
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        super.onCombatTick(x, y, game);
        List<Entity> humans = filterByID(game.getSquareNeighbors(x, y, 1), human.id);
        for (Entity entity : humans) {
            if (!entity.isAlive()) {
                entity.setID(id);
                entity.setAlive(true);
            }
        }
    }

    @Override
    public void entityInit(Entity entity) {
    }
}

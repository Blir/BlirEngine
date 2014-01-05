package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.item.Item;
import blir.engine.item.ItemStack;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.*;


/**
 *
 * @author Blir
 */
public class Human extends CombatEntityType {

    public Human(int id) {
        super(id, "Human", Color.PINK, 20);
    }

    @Override
    public void init() {
        damageMap = new HashMap<>();
        damageMap.put(zombie.id, 3);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        Location loc = Location.wander(x, y, 1);
        game.moveEntity(x, y, loc.x, loc.y);
    }

    @Override
    public void onSpawnTick(Game game) {
        Set<Location> entityLocations = game.getEntityLocations(this);
        Set<Location> emptyLocations = new HashSet<>();
        for (Location entity : entityLocations) {
            ItemStack walls = game.getEntityAt(entity.x, entity.y).getItemStackByID(Item.wall.id);
            if (walls.getAmount() > 0) {
                Location loc = game.getFirstSquareNeighborLocationByID(entity.x, entity.y, 1, zombie.id);
                if (loc != null) {
                    Location wallLoc = Location.towards(entity, loc, 1);
                    game.spawnEntityAt(wallLoc.x, wallLoc.y, wall);
                    walls.changeAmountBy(-1);
                    break;
                }
            }
            emptyLocations.addAll(game.getEmptyLocations(entity.x, entity.y, 1));
        }
        for (Location empty : emptyLocations) {
            if (filterByID(game.getNeighorSlice(empty.x, empty.y, 1), id).size() == 2
                && game.getNeighorSlice(empty.x, empty.y, 2).isEmpty()) {

                game.spawnEntityAt(empty.x, empty.y, this);
            }
        }
    }

    @Override
    public void entityInit(Entity entity) {
        entity.addItemStack(new ItemStack(Item.wall.id));
    }
}

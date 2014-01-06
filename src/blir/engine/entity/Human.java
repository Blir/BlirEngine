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
        super(id, "Human", Color.BLUE, 20);
    }

    @Override
    public void init(Game game) {
        damageMap = new HashMap<>();
        damageMap.put(zombie.id, 3);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        if (filterByID(game.getSquareNeighbors(x, y, 1), juggernaut.id).isEmpty()) {
            Location loc = game.getFirstSquareNeighborLocationByID(x, y, 2, zombie.id);
            if (loc == null) {
                loc = Location.wander(x, y, 1);
            } else {
                loc = Location.away(x, y, loc.x, loc.y, 2);
            }
            game.moveEntity(x, y, loc.x, loc.y);
        }
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

                Map<Entity, Location> mates = filterByID(game.getSquareNeighborLocations(empty.x, empty.y, 1), id);
                for (Map.Entry<Entity, Location> entry : mates.entrySet()) {
                    ItemStack is = entry.getKey().getItemStackByID(Item.fertility.id);
                    if (is.getAmount() > 0) {
                        is.changeAmountBy(-1);
                        game.spawnEntityAt(empty.x, empty.y, this);
                        break;
                    }
                }
            }
        }
    }

    @Override
    public void entityInit(Entity entity) {
        entity.addItemStack(new ItemStack(Item.wall.id));
        entity.addItemStack(new ItemStack(Item.fertility.id, 2));
    }
}

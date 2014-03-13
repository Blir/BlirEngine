package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import blir.engine.game.Team;
import blir.engine.item.Item;
import blir.engine.item.ItemStack;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.*;

/**
 *
 * @author Blir
 */
public class HumanEntityType extends EntityType implements Team {

    int damageDealt;
    int deaths;
    int births;
    int kills;

    public HumanEntityType(int id, int spawnID) {
        super(id, "Human", Color.BLUE, new EntitySpawner(spawnID, "Human Spawner", Color.CYAN, 250, 4, id));
    }

    @Override
    public void init(SinglePlayerGame game) {
    }

    @Override
    public void onSpawnTick(SinglePlayerGame game) {
        Set<Location> entityLocations = game.getEntityLocations(this);
        Set<Location> emptyLocations = new HashSet<>();

        for (Location entity : entityLocations) {
            ItemStack walls = ((Human) game.getEntity(entity.x, entity.y)).getItemStack(Item.wall);
            if (walls.getAmount() > 0) {
                Location loc = game.getFirstSquareNeighborLocation(entity.x, entity.y, 3, zombie.id);
                if (loc != null) {
                    Location wallLoc = Location.towards(entity, loc, 1);
                    if (game.spawnEntity(wallLoc, wall.spawn())) {
                        walls.changeAmountBy(-1);
                    }
                }
            }
            emptyLocations.addAll(game.getEmptyLocations(entity.x, entity.y, 1));
        }

        for (Location empty : emptyLocations) {
            if (filterByID(game.getNeighorSlice(empty.x, empty.y, 1), id).size() == 2
                && game.getNeighorSlice(empty.x, empty.y, 2).isEmpty()) {

                if (game.spawnEntity(empty, spawn())) {
                    births++;
                }
            }
        }
    }

    @Override
    public Entity spawn() {
        Human entity = new Human();
        entity.addItemStack(Item.wall, 2);
        return entity;
    }

    @Override
    public int getScore() {
        return 2 * damageDealt + 4 * births + 15 * kills - 5 * deaths;
    }
}

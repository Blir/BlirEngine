package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.game.Team;
import blir.engine.item.Item;
import blir.engine.item.ItemStack;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.*;
import java.util.logging.Level;

/**
 *
 * @author Blir
 */
public class HumanEntityType extends EntityType implements Team {

    int damageDealt;

    public HumanEntityType(int id, int spawnID) {
        super(id, "Human", Color.BLUE, new EntitySpawner(spawnID, "Human Spawner", Color.CYAN, 50, 4, id));
    }

    @Override
    public void init(Game game) {
    }

    @Override
    public void onSpawnTick(Game game) {
        Set<Location> entityLocations = game.getEntityLocations(this);
        Set<Location> emptyLocations = new HashSet<>();

        for (Location entity : entityLocations) {
            ItemStack walls = ((Human) game.getEntity(entity.x, entity.y)).getItemStack(Item.wall);
            if (walls.getAmount() > 0) {
                Location loc = game.getFirstSquareNeighborLocation(entity.x, entity.y, 3, zombie.id);
                if (loc != null) {
                    //Game.log(Level.FINER, "placing wall");
                    Location wallLoc = Location.towards(entity, loc, 1);
                    game.spawnEntity(wallLoc, wall.spawn());
                    walls.changeAmountBy(-1);
                }
            }
            emptyLocations.addAll(game.getEmptyLocations(entity.x, entity.y, 1));
        }

        for (Location empty : emptyLocations) {
            if (filterByID(game.getNeighorSlice(empty.x, empty.y, 1), id).size() == 2
                && game.getNeighorSlice(empty.x, empty.y, 2).isEmpty()) {

                game.spawnEntity(empty, spawn());
            }
        }
    }

    @Override
    public Entity spawn() {
        Human entity = new Human();
        entity.addItemStack(Item.wall);
        return entity;
    }

    @Override
    public int getScore() {
        return damageDealt;
    }

    @Override
    public String getTeamName() {
        return name;
    }
}

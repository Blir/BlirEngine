package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.game.Team;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Blir
 */
public class ZombieEntityType extends EntityType implements Team {

    Set<Location> toSpawn = new HashSet<>();
    int damageDealt;

    public ZombieEntityType(int id, int spawnID) {
        super(id, "Zombie", Color.GREEN, new EntitySpawner(spawnID, "Zombie Spawner", Color.YELLOW, 50, 5, id));
    }

    @Override
    public Entity spawn() {
        return new Zombie();
    }

    @Override
    public void init(Game game) {
    }

    @Override
    public void onSpawnTick(Game game) {
        Set<Location> temp = new HashSet<>();
        for (Location loc : toSpawn) {
            if (!game.spawnEntity(loc, spawn())) {
                temp.add(loc);
            }
        }
        toSpawn = temp;
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

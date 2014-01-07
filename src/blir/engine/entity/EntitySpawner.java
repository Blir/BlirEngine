package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Set;

/**
 *
 * @author Blir
 */
public class EntitySpawner extends EntityType {

    public final int rate, spawnID, cap;

    private EntitySpawner(int id, String name, Color color, BufferedImage img,
                          int rate, int cap, int spawnID) {
        super(id, name, color, img, null);
        this.rate = rate;
        this.cap = cap;
        this.spawnID = spawnID;
    }

    public EntitySpawner(int id, String name, Color color, int rate, int cap,
                         int spawnID) {
        this(id, name, color, null, rate, cap, spawnID);
    }

    public EntitySpawner(int id, String name, BufferedImage img, int rate,
                         int cap, int spawnID) {
        this(id, name, null, img, rate, cap, spawnID);
    }

    @Override
    public void onSpawnTick(Game game) {
        EntityType toSpawn = game.getEntityType(spawnID);
        Set<Location> existing = game.getEntityLocations(toSpawn);
        if (existing != null && existing.size() < cap) {
            Set<Location> entityLocations = game.getEntityLocations(this);
            for (Location entity : entityLocations) {
                if (game.getEntity(entity.x, entity.y).getTicksLived() % rate == 0) {
                    Set<Location> emptyLocations = game.getEmptyLocations(entity.x, entity.y, 1);
                    int spawned = 0;
                    for (Location loc : emptyLocations) {
                        if (spawned + existing.size() < cap) {
                            spawned++;
                            game.spawnEntity(loc.x, loc.y, toSpawn.spawn());
                        }
                    }
                }
            }
        }
    }

    @Override
    public void init(Game game) {
    }
}

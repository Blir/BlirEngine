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
    public void onMoveTick(int x, int y, Game game) {
    }

    @Override
    public void onSpawnTick(Game game) {
        EntityType toSpawn = game.getEntityTypeByID(spawnID);
        Set<Location> existing = game.getEntityLocations(toSpawn);
        if (existing != null && existing.size() < cap) {
            Set<Location> entityLocations = game.getEntityLocations(this);
            for (Location entity : entityLocations) {
                if (game.getEntityAt(entity.x, entity.y).getTicksLived() % rate == 0) {
                    Set<Location> emptyLocations = game.getEmptyLocations(entity.x, entity.y, 1);
                    for (Location loc : emptyLocations) {
                        game.spawnEntityAt(loc.x, loc.y, toSpawn);
                    }
                }
            }
        }
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
    }

    @Override
    public void init() {
    }

    @Override
    public void entityInit(Entity entity) {
    }
}

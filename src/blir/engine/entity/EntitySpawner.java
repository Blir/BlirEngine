package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

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
    public void onSpawnTick(List<Location> entityLocations, Game game) {
        if (entityLocations.size() < cap) {
            for (Location loc : entityLocations) {
                Entity entity = game.getEntityAt(loc.x, loc.y);
                if (entity.getTicksLived() % rate == 0) {
                    game.spawnEntityAt(loc.x, loc.y, spawnID);
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
}

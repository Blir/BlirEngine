package blir.engine.slot;

import blir.engine.game.Game;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Blir
 */
public class EntitySpawner extends EntityType {

    public final int rate, spawnID, cap;
    
    private EntitySpawner(int id, String name, Color color, BufferedImage img, int rate, int cap, int spawnID) {
        super(id, name, color, img, null);
        this.rate = rate;
        this.cap = cap;
        this.spawnID = spawnID;
    }
    
    public EntitySpawner(int id, String name, Color color, int rate, int cap, int spawnID) {
        this(id, name, color, null, rate, cap, spawnID);
    }
    
    public EntitySpawner(int id, String name, BufferedImage img, int rate, int cap, int spawnID) {
        this(id, name, null, img, rate, cap, spawnID);
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onSpawnTick(Game game) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

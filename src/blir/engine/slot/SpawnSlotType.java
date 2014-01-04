package blir.engine.slot;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Blir
 */
public class SpawnSlotType extends SlotType {

    public final int rate, spawnID, cap;
    
    private SpawnSlotType(int id, String name, Color color, BufferedImage img, int rate, int cap, int spawnID) {
        super(id, name, color, img, null);
        this.rate = rate;
        this.cap = cap;
        this.spawnID = spawnID;
    }
    
    public SpawnSlotType(int id, String name, Color color, int rate, int cap, int spawnID) {
        this(id, name, color, null, rate, cap, spawnID);
    }
    
    public SpawnSlotType(int id, String name, BufferedImage img, int rate, int cap, int spawnID) {
        this(id, name, null, img, rate, cap, spawnID);
    }

    @Override
    public int onUpdate(int x, int y) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

package blir.engine.slot;

import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Blir
 */
public abstract class SlotType {

    public final int id;
    public final Color color;
    public final BufferedImage img;
    public final String name;
    public final SpawnSlotType spawner;

    SlotType(int id, String name, Color color, BufferedImage img,
                     SpawnSlotType spawner) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.spawner = spawner;
        this.img = img;
    }

    public SlotType(int id, String name, Color color, SpawnSlotType spawner) {
        this(id, name, color, null, spawner);
    }

    public SlotType(int id, String name, BufferedImage img,
                    SpawnSlotType spawner) {
        this(id, name, null, img, spawner);
    }

    public SlotType(int id, String name, Color color) {
        this(id, name, color, null, null);
    }
    
    public SlotType(int id, String name, BufferedImage img) {
        this(id, name, null, img, null);
    }
    
    public abstract int onUpdate(int x, int y);

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}

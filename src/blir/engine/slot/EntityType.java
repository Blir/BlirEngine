package blir.engine.slot;

import blir.engine.game.Game;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Blir
 */
public abstract class EntityType {

    public static final Original original = new Original(0);
    
    public static List<Entity> filterByID(List<Entity> slots, int id) {
        List<Entity> filtered = new LinkedList<>();
        for(Entity slot : slots) {
            if (slot.id == id) {
                filtered.add(slot);
            }
        }
        return filtered;
    }
    
    public final int id;
    public final Color color;
    public final BufferedImage img;
    public final String name;
    public final EntitySpawner spawner;
    
    EntityType(int id, String name, Color color, BufferedImage img,
                     EntitySpawner spawner) {
        this.id = id;
        this.name = name;
        this.color = color;
        this.spawner = spawner;
        this.img = img;
    }

    public EntityType(int id, String name, Color color, EntitySpawner spawner) {
        this(id, name, color, null, spawner);
    }

    public EntityType(int id, String name, BufferedImage img,
                    EntitySpawner spawner) {
        this(id, name, null, img, spawner);
    }

    public EntityType(int id, String name, Color color) {
        this(id, name, color, null, null);
    }
    
    public EntityType(int id, String name, BufferedImage img) {
        this(id, name, null, img, null);
    }
    
    public abstract void onMoveTick(int x, int y, Game game);
    
    public abstract void onSpawnTick(Game game);
    
    public abstract void onCombatTick(int x, int y, Game game);

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}

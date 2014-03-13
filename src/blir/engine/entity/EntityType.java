package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import blir.engine.util.Location;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 *
 * @author Blir
 */
public abstract class EntityType {

    public static final OriginalEntityType original = new OriginalEntityType(0);
    public static final WallEntityType wall = new WallEntityType(1);
    public static final ArcherEntityType archer = new ArcherEntityType(2, 3);
    public static final WarriorEntityType warrior = new WarriorEntityType(4, 5);
    public static final WizardEntityType wizard = new WizardEntityType(6, 7);
    public static final HumanEntityType human = new HumanEntityType(8, 9);
    public static final ZombieEntityType zombie = new ZombieEntityType(10, 11);
    public static final JuggernautEntityType juggernaut = new JuggernautEntityType(12, 13);
    public static final PlayerEntityType player = new PlayerEntityType(14);
    //public static final GenericEntityType ground = new GenericEntityType(15, "Ground", Color.GREEN);
    public static final EnemyEntityType enemy = new EnemyEntityType(16);
    public static final MissileEntityType missile = new MissileEntityType(17);

    public static List<Entity> filterByID(List<Entity> list, int id) {
        List<Entity> filtered = new LinkedList<>();
        for (Entity slot : list) {
            if (slot.id == id) {
                filtered.add(slot);
            }
        }
        return filtered;
    }
    
    public static Map<Entity, Location> filterByID(Map<Entity, Location> map, int id) {
        Map<Entity, Location> filtered = new HashMap<>();
        for (Map.Entry<Entity, Location> entry : map.entrySet()) {
            if (entry.getKey().id == id) {
                filtered.put(entry.getKey(), entry.getValue());
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

    public abstract void init(SinglePlayerGame game);

    public abstract void onSpawnTick(SinglePlayerGame game);
    
    public Entity spawn() {
        return new GenericEntity(id);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return name;
    }
}

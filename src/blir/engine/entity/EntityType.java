package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.*;

/**
 *
 * @author Blir
 */
public abstract class EntityType {

    public static final Original original = new Original(0);
    public static final Wall wall = new Wall(1);
    public static final Archer archer = new Archer(2, 3);
    public static final Warrior warrior = new Warrior(4, 5);
    public static final Wizard wizard = new Wizard(6, 7);
    public static final Human human = new Human(8);
    public static final Zombie zombie = new Zombie(9);

    public static List<Entity> filterByID(List<Entity> list, int id) {
        List<Entity> filtered = new LinkedList<>();
        for (Entity slot : list) {
            if (slot.getID() == id) {
                filtered.add(slot);
            }
        }
        return filtered;
    }
    
    public static Map<Entity, Location> filterByID(Map<Entity, Location> map, int id) {
        Map<Entity, Location> filtered = new HashMap<>();
        for (Map.Entry<Entity, Location> entry : map.entrySet()) {
            if (entry.getKey().getID() == id) {
                filtered.put(entry.getKey(), entry.getValue());
            }
        }
        return filtered;
    }

    public static int damageByMap(List<Entity> slots, Map<Integer, Integer> map,
                                  Game game) {
        int totalDmg = 0;
        for (Entity slot : slots) {
            Integer dmg = map.get(slot.getID());
            if (dmg != null) {
                totalDmg += dmg;
                slot.damage(game, dmg);
            }
        }
        return totalDmg;
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

    public abstract void init();
    
    public abstract void entityInit(Entity entity);

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

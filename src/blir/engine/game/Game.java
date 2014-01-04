package blir.engine.game;

import blir.engine.BlirEngine;
import blir.engine.gui.GameGUI;
import blir.engine.item.Item;
import blir.engine.slot.Entity;
import blir.engine.slot.EntityType;
import blir.engine.util.Location;
import java.util.*;
import java.util.logging.Level;

/**
 *
 * @author Travis
 */
public abstract class Game implements Runnable {

    public static final GameOfLife gameOfLife = new GameOfLife();

    public static void log(Level level, String msg, Throwable thrown) {
        BlirEngine.LOGGER.log(level, msg, thrown);
    }

    public static void log(Level level, String msg, Object... params) {
        BlirEngine.LOGGER.log(level, msg, params);
    }

    public final String name;

    final GameGUI gui = new GameGUI(this);
    final Map<Integer, EntityType> entityTypes = new HashMap<>();
    final Map<Integer, Item> itemTypes = new HashMap<>();
    Entity[][] thisTick;
    Entity[][] nextTick;
    int tick;

    public Game(String name) {
        this.name = name;
    }

    public abstract void init();

    public void registerItemType(Item item) {
        itemTypes.put(item.id, item);
    }

    public void registerEntityType(EntityType type) {
        entityTypes.put(type.id, type);
        if (type.spawner != null) {
            entityTypes.put(type.spawner.id, type.spawner);
        }
    }

    public List<Entity> getNeighbors(int row, int col, int dist) {
        List<Entity> neighbors = new LinkedList<>();

        for (int x = row - dist; x <= row + dist; x++) {
            if (x > 0 && x < thisTick.length - 1) {
                for (int y = col - dist; y <= col + dist; y++) {
                    if (y > 0 && y < thisTick[x].length - 1 && thisTick[x][y] != null
                        && !(x == row && y == col)) {

                        neighbors.add(thisTick[x][y]);
                    }
                }
            }
        }

        return neighbors;
    }

    public List<Location> getEmptyLocations(int row, int col, int dist) {
        List<Location> locations = new LinkedList<>();
        for (int x = row - dist; x <= row + dist; x++) {
            if (x > 0 && x < thisTick.length - 1) {
                for (int y = col - dist; y <= col + dist; y++) {
                    if (y > 0 && y < thisTick[x].length - 1 && thisTick[x][y] == null
                        && !(x == row && y == col)) {

                        locations.add(new Location(x, y));
                    }
                }
            }
        }
        return locations;
    }

    public Entity getEntityAt(int x, int y) {
        return thisTick[x][y];
    }

    public boolean spawnEntityAt(int x, int y, Entity entity) {
        if (nextTick[x][y] == null) {
            nextTick[x][y] = entity;
            return true;
        }
        return false;
    }

    public EntityType getSlotTypeForID(int id) {
        return entityTypes.get(id);
    }

    public Item getItemForID(int id) {
        return itemTypes.get(id);
    }

    public Collection<EntityType> getSlotTypes() {
        return entityTypes.values();
    }

    public Collection<Item> getItemTypes() {
        return itemTypes.values();
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Game ? ((Game) obj).name.equals(this.name) : false;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}

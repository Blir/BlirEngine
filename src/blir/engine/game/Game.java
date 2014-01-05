package blir.engine.game;

import blir.engine.BlirEngine;
import blir.engine.swing.GameGUI;
import blir.engine.item.Item;
import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
import blir.engine.util.Location;

import java.util.*;
import java.util.logging.Level;

/**
 *
 * @author Travis
 */
public abstract class Game implements Runnable {

    public static final GameOfLife gameOfLife = new GameOfLife();
    public static final ArchIorZard archiorzard = new ArchIorZard();
    public static final Apocalypse apocalypse = new Apocalypse();

    public static void log(Level level, String msg, Throwable thrown) {
        BlirEngine.LOGGER.log(level, msg, thrown);
    }

    public static void log(Level level, String msg, Object... params) {
        BlirEngine.LOGGER.log(level, msg, params);
    }

    public static void log(Level level, String msg) {
        BlirEngine.LOGGER.log(level, msg);
    }

    public final String name;
    public final int PIXEL_SIZE;

    final GameGUI gui;
    final Map<Integer, EntityType> entityTypes = new HashMap<>();
    final Map<Integer, Item> itemTypes = new HashMap<>();
    final Scoreboard scoreboard = new Scoreboard();

    GameState state;
    final Map<EntityType, Set<Location>> entityLocations = new HashMap<>();
    Entity[][] thisTick;
    Entity[][] nextTick;
    int tick;
    int speed;

    public Game(String name, int spawnInit, int pixelSize) {
        this.name = name;
        this.PIXEL_SIZE = pixelSize;
        this.gui = new GameGUI(this, spawnInit);
    }

    public abstract void init();

    public abstract void reset();
    
    public abstract int size();

    public void registerItemType(Item item) {
        itemTypes.put(item.id, item);
    }

    public void registerEntityType(EntityType type) {
        entityTypes.put(type.id, type);
        type.init();
        if (type.spawner != null) {
            registerEntityType(type.spawner);
        }
    }

    public List<Entity> getSquareNeighbors(int row, int col, int dist) {
        List<Entity> neighbors = new LinkedList<>();

        for (int x = row - dist; x <= row + dist; x++) {
            if (isInBounds(x)) {
                for (int y = col - dist; y <= col + dist; y++) {
                    if (isInBounds(y) && thisTick[x][y] != null
                        && !(x == row && y == col)) {

                        neighbors.add(thisTick[x][y]);
                    }
                }
            }
        }

        return neighbors;
    }

    public List<Entity> getNeighorSlice(int row, int col, int dist) {
        List<Entity> neighbors = new LinkedList<>();

        for (int x = row - dist; x <= row + dist; x++) {
            if (isInBounds(x)) {
                for (int y = col - dist; y <= col + dist; y++) {
                    if (isInBounds(y) && thisTick[x][y] != null
                        && Math.abs((row - x)) + Math.abs((col - y)) == dist
                        && !(x == row && y == col)) {

                        neighbors.add(thisTick[x][y]);
                    }
                }
            }
        }

        return neighbors;
    }

    public Map<Entity, Location> getSquareNeighborLocations(int row, int col,
                                                            int dist) {

        Map<Entity, Location> neighborLocations = new HashMap<>();
        for (int x = row - dist; x <= row + dist; x++) {
            if (isInBounds(x)) {
                for (int y = col - dist; y <= col + dist; y++) {
                    if (isInBounds(y) && thisTick[x][y] != null
                        && !(x == row && y == col)) {

                        neighborLocations.put(thisTick[x][y], new Location(x, y));
                    }
                }
            }
        }
        return neighborLocations;
    }
    
    public Location getFirstSquareNeighborLocationByID(int row, int col, int dist, int id) {
        for (int x = row - dist; x <= row + dist; x++) {
            if (isInBounds(x)) {
                for (int y = col - dist; y <= col + dist; y++) {
                    if (isInBounds(y) && thisTick[x][y] != null
                        && !(x == row && y == col) && thisTick[x][y].getID() == id) {

                        return new Location(x, y);
                    }
                }
            }
        }
        return null;
    }

    public Set<Location> getEmptyLocations(int row, int col, int dist) {
        Set<Location> locations = new HashSet<>();
        for (int x = row - dist; x <= row + dist; x++) {
            if (isInBounds(x)) {
                for (int y = col - dist; y <= col + dist; y++) {
                    if (isInBounds(y) && thisTick[x][y] == null
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

    public void placeEntityAt(int x, int y, Entity entity) {
        if (state != null) {
            throw new IllegalStateException("game currently ticking");
        }
        thisTick[x][y] = entity;
    }

    public boolean spawnEntityAt(int x, int y, EntityType type) {
        if (state != GameState.SPAWN_TICK) {
            throw new IllegalStateException("not in spawn tick");
        }
        if (isInBounds(x, y) && nextTick[x][y] == null) {
            Entity entity = new Entity(type.id);
            type.entityInit(entity);
            nextTick[x][y] = entity;
            return true;
        }
        return false;
    }

    public boolean moveEntity(int fromX, int fromY, int toX, int toY) {
        if (state != GameState.MOVE_TICK) {
            throw new IllegalStateException("not in move tick");
        }
        if (isInBounds(toX, toY) && nextTick[toX][toY] == null) {
            nextTick[toX][toY] = thisTick[fromX][fromY];
            thisTick[fromX][fromY].setAlive(false);
            return true;
        }
        return false;
    }
    
    public Set<Location> getEntityLocations(EntityType type) {
        return entityLocations.get(type);
    }

    public boolean isInBounds(int... coords) {
        for (int coord : coords) {
            if (coord < 0 || coord >= thisTick.length) {
                return false;
            }
        }
        return true;
    }

    public EntityType getEntityTypeByID(int id) {
        return entityTypes.get(id);
    }

    public Item getItemForID(int id) {
        return itemTypes.get(id);
    }

    public Collection<EntityType> getEntityTypes() {
        return entityTypes.values();
    }

    public Collection<Item> getItemTypes() {
        return itemTypes.values();
    }

    public GameState getState() {
        return state;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
    
    public void updateScoreboard() {
        gui.setTitle(scoreboard.toString(name));
    }

    @Override
    public String toString() {
        return name;
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

package blir.engine.game;

import blir.engine.BlirEngine;
import blir.engine.swing.GameGUI;
import blir.engine.item.Item;
import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
import blir.engine.swing.Pixel;
import blir.engine.util.Location;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

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
    public static final ScrollingGame test = new ScrollingGame("test", 0, 15);

    /**
     * Delegate Logger method.
     *
     * @param level  log level
     * @param msg    log message
     * @param thrown exception
     */
    public static void log(Level level, String msg, Throwable thrown) {
        BlirEngine.LOGGER.log(level, msg, thrown);
    }

    /**
     * Delegate Logger method.
     *
     * @param level  log level
     * @param msg    log format string message
     * @param params log parameters
     * @see
     * http://docs.oracle.com/javase/7/docs/api/java/util/Formatter.html#syntax
     */
    public static void log(Level level, String msg, Object... params) {
        BlirEngine.LOGGER.log(level, msg, params);
    }

    /**
     * Delegate Logger method.
     *
     * @param level log level
     * @param msg   log message
     */
    public static void log(Level level, String msg) {
        BlirEngine.LOGGER.log(level, msg);
    }

    /**
     * The name of the Game.
     */
    public final String name;

    /**
     * The size of each pixel rendered to represent an entity.
     */
    public final int PIXEL_SIZE;

    final GameGUI gui;
    final Map<Integer, EntityType> entityTypes = new HashMap<>();
    final Map<Integer, Item> itemTypes = new HashMap<>();
    final Scoreboard scoreboard;

    GameState state;
    final Map<EntityType, Set<Location>> entityLocations = new HashMap<>();
    Entity[][] thisTick;
    Entity[][] nextTick;
    int tick;
    int speed;

    /**
     * Creates a new Game object.
     *
     * @param name      the name of the Game
     * @param spawnInit the Entity ID to initialize for spawning where the
     *                  player clicks
     * @param pixelSize the size of each pixel rendered to represent an entity
     */
    public Game(String name, int spawnInit, int pixelSize) {
        this.name = name;
        this.PIXEL_SIZE = pixelSize;
        this.gui = new GameGUI(this, spawnInit);
        this.scoreboard = new Scoreboard(this);
    }

    /**
     * Called once to initialize the Game. This is where a Game should register
     * EntityTypes, Items, set up Alliances, Teams, and the Scoreboard, set GUIs
     * to be visible and start the Game.
     */
    public abstract void init();

    /**
     * Called to reset this Game. What you do in this method depends on the
     * Game; you may wish to reset counters, clear all entities, or do nothing
     * at all.
     */
    public abstract void reset();

    /**
     * The rendered size of this Game. Determines the number of Entities drawn
     * at once.
     *
     * @return the size of the Game
     */
    public abstract int size();

    /**
     * Allows Games to dynamically control the look of each pixel (typically
     * Entities). The size of the array returned should reflect the value
     * returned by size(). This implementation is subject to change as it is not
     * exactly efficient.
     *
     * @return the Pixel objects representing the pixels to be rendered
     */
    public abstract Pixel[][] getDisplay();

    /**
     * Registers the given Item.
     *
     * @param item the Item to register
     */
    public void registerItemType(Item item) {
        itemTypes.put(item.id, item);
    }

    /**
     * Register the given EntityType.
     *
     * @param type the EntityType to register
     */
    public void registerEntityType(EntityType type) {
        entityTypes.put(type.id, type);
        type.init(this);
        if (type.spawner != null) {
            registerEntityType(type.spawner);
        }
    }

    /**
     * Gets the neighbors around the specified location in a square pattern up
     * to the specified distance away. In this algorithm, (1,1) would be
     * considered to be 1 block away from (2,2).
     *
     * @param row  x coordinate around which to search
     * @param col  y coordinate around which to search
     * @param dist distance out from the coordinates to search
     * @return a List of the Entities neighboring the specified x & y
     */
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

    /**
     * Gets the neighbors around the specified location in a diamond pattern at
     * exactly the specified distance. In this algorithm, (1,1) would be
     * considered to be 2 blocks away from (2,2).
     *
     * @param row  x coordinate around which to search
     * @param col  y coordinate around which to search
     * @param dist distance out from the coordinates to search
     * @return a List of the Entities neighboring the specified x & y
     */
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

    /**
     * Gets the neighbors around the specified location in a square pattern up
     * to the specified distance away, as well as their Locations.
     *
     * @param row  x coordinate around which to search
     * @param col  y coordinate around which to search
     * @param dist distance out from the coordinates to search
     * @return a Map of each neighboring Entity and its Location
     */
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

    /**
     * Returns the Location of the first Entity of the specified ID encountered
     * when searching around the specified location in a square pattern, up to
     * the specified distance away. This will not return the closest Entity of
     * that ID.
     *
     * @param row  x coordinate around which to search
     * @param col  y coordinate around which to search
     * @param dist distance out from the coordinates to search
     * @param id   the Entity ID for which to search
     * @return the Location of the first such Entity encountered
     */
    public Location getFirstSquareNeighborLocation(int row, int col, int dist,
                                                   int id) {
        for (int x = row - dist; x <= row + dist; x++) {
            if (isInBounds(x)) {
                for (int y = col - dist; y <= col + dist; y++) {
                    if (isInBounds(y) && thisTick[x][y] != null
                        && !(x == row && y == col) && thisTick[x][y].id == id) {

                        return new Location(x, y);
                    }
                }
            }
        }
        return null;
    }

    /**
     * Returns all empty (occupied by no Entities) Locations found by searching
     * in a square pattern around the specified location out to the specified
     * distance.
     *
     * @param row  x coordinate around which to search
     * @param col  y coordinate around which to search
     * @param dist distance out from the coordinates to search
     * @return a Set of the empty Locations found
     */
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

    /**
     * Returns the Entity at the given Location, null if no such Entity.
     *
     * @param loc the Location of the Entity to get
     * @return the Entity at the Location
     */
    public Entity getEntity(Location loc) {
        return getEntity(loc.x, loc.y);
    }

    /**
     * Returns the Entity at the given location, null if no such Entity.
     *
     * @param x the x coordinate of the Entity to get
     * @param y the y coordinate of the Entity to get
     * @return the Entity at the location
     */
    public Entity getEntity(int x, int y) {
        return thisTick[x][y];
    }

    /**
     * Typically used by the Game GUIs for when the player clicks Entities to
     * remove them. Do not use this method while the Game is ticking.
     *
     * @param x the x coordinate of the Entity to remove
     * @param y the y coordinate of the Entity to remove
     */
    public void removeEntity(int x, int y) {
        if (state != null) {
            throw new IllegalStateException("game currently ticking");
        }
        thisTick[x][y] = null;
    }

    /**
     * Typically used by the Game GUIs for when the player clicks empty
     * locations to place Entities. Do not use this method while the Game is
     * ticking.
     *
     * @param x  the x coordinate of the Entity to place
     * @param y  the y coordinate of the Entity to place
     * @param id the ID of the Entity to place
     */
    public void placeEntity(int x, int y, int id) {
        if (state != null) {
            throw new IllegalStateException("game currently ticking");
        }
        Entity entity = getEntityType(id).spawn();
        entity.setLocation(x, y);
        thisTick[x][y] = entity;
    }

    /**
     * Spawns the given Entity at the given Location. This method may only be
     * called during the spawn tick. The Entity will only be spawned if there is
     * no Entity at the given Location.
     *
     * @param loc    the Location at which to spawn the Entity
     * @param entity the Entity to spawn
     * @return true if the Entity was spawned, false otherwise
     */
    public boolean spawnEntity(Location loc, Entity entity) {
        return spawnEntity(loc.x, loc.y, entity);
    }

    /**
     * Spawns the given Entity at the specified Location. This method may only
     * be called during the spawn tick. The Entity will only be spawned if there
     * is no Entity at the specified location.
     *
     * @param x      the x coordinate at which to spawn the Entity
     * @param y      the y coordinate at which to spawn the Entity
     * @param entity the Entity to spawn
     * @return true if the Entity was spawned, false otherwise
     */
    public boolean spawnEntity(int x, int y, Entity entity) {
        if (state != GameState.SPAWN_TICK) {
            throw new IllegalStateException("not in spawn tick");
        }
        if (isInBounds(x, y) && nextTick[x][y] == null) {
            nextTick[x][y] = entity;
            entity.setLocation(x, y);
            return true;
        }
        return false;
    }

    /**
     * Moves the Entity at the specified location to the given Location. This
     * method may only be called during the move tick. The Entity will only be
     * moved if there is no Entity at the location to which the entity is to be
     * moved.
     *
     * @param fromX the x coordinate of the Entity to move
     * @param fromY the y coordinate of the Entity to move
     * @param to    the Location which the Entity is to be moved to
     * @return true if the Entity was moved, false otherwise
     */
    public boolean moveEntity(int fromX, int fromY, Location to) {
        return moveEntity(fromX, fromY, to.x, to.y);
    }

    /**
     * Moves the Entity at the given Location to the specified location. This
     * method may only be called during the move tick. The Entity will only be
     * moved if there is no Entity at the location to which the entity is to be
     * moved.
     *
     * @param from the Location from which the Entity is to be moved
     * @param toX  the x coordinate to which the Entity is to be moved
     * @param toY  the y coordinate to which the Entity is to be moved
     * @return true if the Entity was moved, false otherwise
     */
    public boolean moveEntity(Location from, int toX, int toY) {
        return moveEntity(from.x, from.y, toX, toY);
    }

    /**
     * Moves the Entity at the given Location to the given Location. This method
     * may only be called during the move tick. The Entity will only be moved if
     * there is no Entity at the location to which the entity is to be moved.
     *
     * @param from the Location from which the Entity is to be moved
     * @param to   the Location to which the Entity is to be moved
     * @return true if the Entity was moved, false otherwise
     */
    public boolean moveEntity(Location from, Location to) {
        return moveEntity(from.x, from.y, to.x, to.y);
    }

    /**
     * Moves the Entity at the specified location to the specified location.
     * This method may only be called during the move tick. The Entity will only
     * be moved if there is no Entity at the location to which the entity is to
     * be moved.
     *
     * @param fromX the x coordinate of the Entity to move
     * @param fromY the y coordinate of the Entity to move
     * @param toX   the x coordinate to which the Entity is to be moved
     * @param toY   the y coordinate to which the Entity is to be moved
     * @return true if the Entity was moved, false otherwise
     */
    public boolean moveEntity(int fromX, int fromY, int toX, int toY) {
        if (state != GameState.MOVE_TICK) {
            throw new IllegalStateException("not in move tick");
        }
        if (isInBounds(toX, toY) && nextTick[toX][toY] == null) {
            nextTick[toX][toY] = thisTick[fromX][fromY];
            thisTick[fromX][fromY].setLocation(toX, toY);
            return true;
        }
        return false;
    }

    /**
     * Returns the Locations of all Entities of the specified EntityType.
     *
     * @param type the EntityType for which to return the Locations of
     * @return the Set of Locations
     */
    public Set<Location> getEntityLocations(EntityType type) {
        return entityLocations.get(type);
    }

    /**
     * Return whether or not the given coordinates are in bounds. Assumes the
     * array of Entities is square and non-null.
     *
     * @param coords the coordinates whose bounds are to be tested
     * @return true if all of the coordinates are in bounds, false otherwise
     */
    public boolean isInBounds(int... coords) {
        for (int coord : coords) {
            if (coord < 0 || coord >= thisTick.length) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets the EntityType with the specified ID.
     *
     * @param id the ID of the EntityType to get
     * @return the EntityType with the specified ID
     */
    public EntityType getEntityType(int id) {
        return entityTypes.get(id);
    }

    /**
     * Gets the Item with the specified ID.
     * 
     * @param id the ID of the Item to get
     * @return the Item with the specified ID
     */
    public Item getItem(int id) {
        return itemTypes.get(id);
    }

    /**
     * Returns a Collection of all registered EntityTypes for this Game.
     * 
     * @return the Collection of all registered EntityTypes
     */
    public Collection<EntityType> getEntityTypes() {
        return entityTypes.values();
    }

    /**
     * Returns a Collection of all registered Items for this Game.
     * 
     * @return the Collection of all registered Items
     */
    public Collection<Item> getItemTypes() {
        return itemTypes.values();
    }

    /**
     * Returns the current state of the Game.
     * 
     * @return the current state
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets the run speed of this Game.
     * 
     * @param speed the new run speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Returns the current run speed of this Game.
     * 
     * @return the current run speed
     */
    public int getSpeed() {
        return speed;
    }

    /**
     * Returns the current tick.
     * 
     * @return the current tick
     */
    public int getTick() {
        return tick;
    }

    /**
     * Resets the tick counter to 0.
     */
    public void resetTicks() {
        tick = 0;
    }

    /**
     * Returns the number of frames the GamePanel has rendered so far.
     * 
     * @return the number of frames rendered so far
     */
    public int getFrames() {
        return gui.getPanelFrames();
    }

    /**
     * Updates the Game's Scoreboard.
     */
    public void updateScoreboard() {
        gui.setTitle(scoreboard.toString());
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

    /**
     * Registers the given KeyListener on the GameGUI.
     * 
     * @param listener the KeyListener to register
     */
    public void registerKeyListener(KeyListener listener) {
        gui.addKeyListener(listener);
    }

    /**
     * Registers the given MouseListener on the GameGUI.
     * 
     * @param listener the MouseListener to register
     */
    public void registerMouseListener(MouseListener listener) {
        gui.addMouseListener(listener);
    }
}

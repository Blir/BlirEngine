package blir.engine.game;

import blir.engine.swing.GameGUI;
import blir.engine.item.Item;
import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
import blir.engine.swing.Animation;
import blir.engine.swing.Pixel;
import blir.engine.swing.PixelType;
import blir.engine.util.Location;

import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.logging.Level;

/**
 *
 * @author Travis
 */
public abstract class SinglePlayerGame extends Game {

    /**
     * The size of each pixel rendered to represent an entity.
     */
    int pixelSize;

    /**
     * The length and width of the square of pixels to be rendered.
     */
    int pixels;

    /**
     * The recommended size for a window.
     */
    public final int RECOMMENDED_WINDOW_SIZE;

    final GameGUI gui;
    final Map<Integer, EntityType> entityTypes = new HashMap<>();
    final Map<Integer, Item> itemTypes = new HashMap<>();
    final Map<Integer, PixelType> pixelTypes = new HashMap<>();
    final Scoreboard scoreboard;

    GameState state;
    final Map<EntityType, Set<Location>> entityLocations = new HashMap<>();
    final List<Animation> animations = new LinkedList<>();
    Entity[][] thisTick;
    Entity[][] nextTick;
    int tick;
    int speed;

    {
        registerPixelType(PixelType.emptyPixel);
    }

    /**
     * Creates a new Game object.
     *
     * @param name      the name of the Game
     * @param spawnInit the Entity ID to initialize for spawning where the
     *                  player clicks
     * @param pixelSize the size of each pixel rendered to represent an entity
     * @param pixels    the sqrt of the number of pixels to be rendered
     */
    public SinglePlayerGame(String name, int spawnInit, int pixelSize,
                            int pixels) {
        super(name);
        this.pixelSize = pixelSize;
        this.gui = new GameGUI(this, spawnInit);
        this.scoreboard = new Scoreboard(this);
        this.pixels = pixels;
        this.RECOMMENDED_WINDOW_SIZE = this.pixelSize * this.pixels + 75;
    }

    /**
     * Called to reset this SinglePlayerGame. What you do in this method depends
     * on the SinglePlayerGame; you may wish to reset counters, clear all
     * entities, or do nothing at all.
     */
    public abstract void reset();

    /**
     * The rendered size of this SinglePlayerGame. Determines the number of
     * Entities drawn at once.
     *
     * @return the size of the SinglePlayerGame
     */
    public abstract int size();

    /**
     * Allows SinglePlayerGames to dynamiaddAnimationlly control the look of
     * each pixel (typiaddAnimationlly Entities). The size of the array returned
     * should reflect the value returned by size(). This implementation is
     * subject to change as it is not exactly efficient.
     *
     * @return the Pixel objects representing the pixels to be rendered
     */
    public abstract Pixel[][] getDisplay();
    
    public int getPixelSize() {
        return pixelSize;
    }
    
    public void setPixelSize(int size) {
        this.pixelSize = size;
    }
    
    public int getMapSize() {
        return pixels;
    }
    
    public void setMapSize(int size) {
        this.pixels = size;
    }
    
    public void stop() {
        gui.stop();
    }

    public List<Animation> getAnimations() {
        List<Animation> old = new LinkedList<>(animations);
        animations.clear();
        for (Animation a : old) {
            if (a.isAlive()) {
                animations.add(a);
            }
        }
        return animations;
    }

    public void addAnimation(Animation a) {
        animations.add(a);
    }

    /**
     * Registers the given Item.
     *
     * @param item the Item to register
     */
    public void registerItemType(Item item) {
        log(Level.INFO, "Registering item type %d", item.id);
        itemTypes.put(item.id, item);
    }

    /**
     * Register the given EntityType.
     *
     * @param type the EntityType to register
     */
    public void registerEntityType(EntityType type) {
        log(Level.INFO, "Registering entity type %d", type.id);
        entityTypes.put(type.id, type);
        type.init(this);
        if (type.spawner != null) {
            registerEntityType(type.spawner);
        }
    }

    public void registerPixelType(PixelType type) {
        log(Level.INFO, "Registering pixel type %d", type.id);
        pixelTypes.put(type.id, type);
    }

    /**
     * Gets the neighbors around the specified loaddAnimationtion in a square
     * pattern up to the specified distance away. In this algorithm, (1,1) would
     * be considered to be 1 block away from (2,2).
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
     * Gets the neighbors around the specified loaddAnimationtion in a diamond
     * pattern at exactly the specified distance. In this algorithm, (1,1) would
     * be considered to be 2 blocks away from (2,2).
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
     * Gets the neighbors around the specified loaddAnimationtion in a square
     * pattern up to the specified distance away, as well as their
     * LoaddAnimationtions.
     *
     * @param row  x coordinate around which to search
     * @param col  y coordinate around which to search
     * @param dist distance out from the coordinates to search
     * @return a Map of each neighboring Entity and its LoaddAnimationtion
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
     * Returns the LoaddAnimationtion of the first Entity of the specified ID
     * encountered when searching around the specified loaddAnimationtion in a
     * square pattern, up to the specified distance away. This will not return
     * the closest Entity of that ID.
     *
     * @param row  x coordinate around which to search
     * @param col  y coordinate around which to search
     * @param dist distance out from the coordinates to search
     * @param id   the Entity ID for which to search
     * @return the LoaddAnimationtion of the first such Entity encountered
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
     * Returns all empty (occupied by no Entities) LoaddAnimationtions found by
     * searching in a square pattern around the specified loaddAnimationtion out
     * to the specified distance.
     *
     * @param row  x coordinate around which to search
     * @param col  y coordinate around which to search
     * @param dist distance out from the coordinates to search
     * @return a Set of the empty LoaddAnimationtions found
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
     * Returns the Entity at the given LoaddAnimationtion, null if no such
     * Entity.
     *
     * @param loc the LoaddAnimationtion of the Entity to get
     * @return the Entity at the LoaddAnimationtion
     */
    public Entity getEntity(Location loc) {
        return getEntity(loc.x, loc.y);
    }

    /**
     * Returns the Entity at the given loaddAnimationtion, null if no such
     * Entity.
     *
     * @param x the x coordinate of the Entity to get
     * @param y the y coordinate of the Entity to get
     * @return the Entity at the loaddAnimationtion
     */
    public Entity getEntity(int x, int y) {
        return thisTick[x][y];
    }

    /**
     * TypiaddAnimationlly used by the SinglePlayerGame GUIs for when the player
     * clicks Entities to remove them. Do not use this method while the
     * SinglePlayerGame is ticking.
     *
     * @param x the x coordinate of the Entity to remove
     * @param y the y coordinate of the Entity to remove
     * @return true if an Entity was removed
     */
    public boolean removeEntity(int x, int y) {
        if (state != null) {
            throw new IllegalStateException("game currently ticking");
        }
        boolean success = thisTick[x][y] != null;
        log(Level.INFO, "Removing %d at (%d, %d)", success ? thisTick[x][y].id : -1, x, y);
        thisTick[x][y] = null;
        return success;
    }

    /**
     * TypiaddAnimationlly used by the SinglePlayerGame GUIs for when the player
     * clicks empty loaddAnimationtions to place Entities. Do not use this
     * method while the SinglePlayerGame is ticking.
     *
     * @param x  the x coordinate of the Entity to place
     * @param y  the y coordinate of the Entity to place
     * @param id the ID of the Entity to place
     */
    public void placeEntity(int x, int y, int id) {
        if (state != null) {
            throw new IllegalStateException("game currently ticking");
        }
        log(Level.INFO, "Placing %d at (%d, %d)", id, x, y);
        Entity entity = getEntityType(id).spawn();
        entity.setLocation(x, y);
        thisTick[x][y] = entity;
    }

    /**
     * Spawns the given Entity at the given LoaddAnimationtion. This method may
     * only be addAnimationlled during the spawn tick. The Entity will only be
     * spawned if there is no Entity at the given LoaddAnimationtion.
     *
     * @param loc    the LoaddAnimationtion at which to spawn the Entity
     * @param entity the Entity to spawn
     * @return true if the Entity was spawned, false otherwise
     */
    public boolean spawnEntity(Location loc, Entity entity) {
        return spawnEntity(loc.x, loc.y, entity);
    }

    /**
     * Spawns the given Entity at the specified LoaddAnimationtion. This method
     * may only be addAnimationlled during the spawn tick. The Entity will only
     * be spawned if there is no Entity at the specified loaddAnimationtion.
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
            log(Level.INFO, "Spawning %d at (%d, %d)", entity.id, x, y);
            nextTick[x][y] = entity;
            entity.setLocation(x, y);
            return true;
        }
        return false;
    }

    /**
     * Moves the Entity at the specified loaddAnimationtion to the given
     * LoaddAnimationtion. This method may only be addAnimationlled during the
     * move tick. The Entity will only be moved if there is no Entity at the
     * loaddAnimationtion to which the entity is to be moved.
     *
     * @param fromX the x coordinate of the Entity to move
     * @param fromY the y coordinate of the Entity to move
     * @param to    the LoaddAnimationtion which the Entity is to be moved to
     * @return true if the Entity was moved, false otherwise
     */
    public boolean moveEntity(int fromX, int fromY, Location to) {
        return moveEntity(fromX, fromY, to.x, to.y);
    }

    /**
     * Moves the Entity at the given LoaddAnimationtion to the specified
     * loaddAnimationtion. This method may only be addAnimationlled during the
     * move tick. The Entity will only be moved if there is no Entity at the
     * loaddAnimationtion to which the entity is to be moved.
     *
     * @param from the LoaddAnimationtion from which the Entity is to be moved
     * @param toX  the x coordinate to which the Entity is to be moved
     * @param toY  the y coordinate to which the Entity is to be moved
     * @return true if the Entity was moved, false otherwise
     */
    public boolean moveEntity(Location from, int toX, int toY) {
        return moveEntity(from.x, from.y, toX, toY);
    }

    /**
     * Moves the Entity at the given LoaddAnimationtion to the given
     * LoaddAnimationtion. This method may only be addAnimationlled during the
     * move tick. The Entity will only be moved if there is no Entity at the
     * loaddAnimationtion to which the entity is to be moved.
     *
     * @param from the LoaddAnimationtion from which the Entity is to be moved
     * @param to   the LoaddAnimationtion to which the Entity is to be moved
     * @return true if the Entity was moved, false otherwise
     */
    public boolean moveEntity(Location from, Location to) {
        return moveEntity(from.x, from.y, to.x, to.y);
    }

    /**
     * Moves the Entity at the specified loaddAnimationtion to the specified
     * loaddAnimationtion. This method may only be addAnimationlled during the
     * move tick. The Entity will only be moved if there is no Entity at the
     * loaddAnimationtion to which the entity is to be moved.
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
            //log(Level.INFO, "Moving entity at (%d, %d) to (%d, %d)", fromX, fromY, toX, toY);
            nextTick[toX][toY] = thisTick[fromX][fromY];
            thisTick[fromX][fromY].setLocation(toX, toY);
            return true;
        }
        return false;
    }

    /**
     * Returns the LoaddAnimationtions of all Entities of the specified
     * EntityType.
     *
     * @param type the EntityType for which to return the LoaddAnimationtions of
     * @return the Set of LoaddAnimationtions
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
     * Returns a Collection of all registered EntityTypes for this
     * SinglePlayerGame.
     *
     * @return the Collection of all registered EntityTypes
     */
    public Collection<EntityType> getEntityTypes() {
        return entityTypes.values();
    }

    /**
     * Returns a Collection of all registered Items for this SinglePlayerGame.
     *
     * @return the Collection of all registered Items
     */
    public Collection<Item> getItemTypes() {
        return itemTypes.values();
    }

    public PixelType getPixelType(int id) {
        return pixelTypes.get(id);
    }

    /**
     * Returns the current state of the SinglePlayerGame.
     *
     * @return the current state
     */
    public GameState getState() {
        return state;
    }

    /**
     * Sets the run speed of this SinglePlayerGame.
     *
     * @param speed the new run speed
     */
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    /**
     * Returns the current run speed of this SinglePlayerGame.
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
     * Returns the number of frames the SinglePlayerGamePanel has rendered so
     * far.
     *
     * @return the number of frames rendered so far
     */
    public int getFrames() {
        return gui.getPanelFrames();
    }

    /**
     * Updates the SinglePlayerGame's Scoreboard.
     */
    public void updateScoreboard() {
        gui.setTitle(scoreboard.toString());
    }

    /**
     * Registers the given KeyListener on the SinglePlayerGameGUI.
     *
     * @param listener the KeyListener to register
     */
    public void registerKeyListener(KeyListener listener) {
        gui.addKeyListener(listener);
    }

    /**
     * Registers the given MouseListener on the SinglePlayerGameGUI.
     *
     * @param listener the MouseListener to register
     */
    public void registerMouseListener(MouseListener listener) {
        gui.addMouseListener(listener);
    }
}

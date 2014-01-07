package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.util.Location;

/**
 *
 * @author Blir
 */
public abstract class Entity {

    public final int id;

    int x, y;
    boolean alive = true;
    int ticksLived;

    public Entity(int id) {
        this.id = id;
    }

    public abstract void onMoveTick(int x, int y, Game game);

    public abstract void onCombatTick(int x, int y, Game game);

    public boolean isAlive() {
        return alive;
    }

    public boolean setAlive(boolean alive) {
        return this.alive = alive;
    }

    public void tick() {
        ticksLived++;
    }

    public int getTicksLived() {
        return ticksLived;
    }

    public void setLocation(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(Location loc) {
        this.x = loc.x;
        this.y = loc.y;
    }

    public void setX(int x) {
        this.x = y;
    }

    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }
}

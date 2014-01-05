package blir.engine.entity;

import blir.engine.game.Game;
import blir.engine.game.GameState;

/**
 *
 * @author Blir
 */
public class Entity {

    private int id;
    private boolean alive = true;
    private int dmg;
    private int ticksLived;

    public Entity(int id) {
        this.id = id;
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean setAlive(boolean alive) {
        return this.alive = alive;
    }

    public void damage(Game game, int dmg) {
        if (game.getState() != GameState.COMBAT_TICK) {
            throw new IllegalStateException("not in combat tick");
        }
        this.dmg += dmg;
    }

    public void heal(Game game, int heal) {
        if (game.getState() != GameState.COMBAT_TICK) {
            throw new IllegalStateException("not in combat tick");
        }
        this.dmg -= heal;
    }

    public int getDamage() {
        return dmg;
    }

    public void tick() {
        alive = true;
        ticksLived++;
    }

    public int getTicksLived() {
        return ticksLived;
    }
}

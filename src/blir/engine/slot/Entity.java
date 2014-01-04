package blir.engine.slot;

/**
 *
 * @author Blir
 */
public class Entity {
    
    public final int id;
    
    private boolean alive = true;
    
    public Entity(int id) {
        this.id = id;
    }
    
    public boolean isAlive() {
        return alive;
    }
    
    public boolean setAlive(boolean alive) {
        return this.alive = alive;
    }
}

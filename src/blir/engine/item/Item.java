package blir.engine.item;

/**
 *
 * @author Blir
 */
public abstract class Item {
    
    public static final GenericItem wall = new GenericItem(0);
    
    public final int id;
    
    public Item(int id) {
        this.id = id;
    }
    
    @Override
    public int hashCode() {
        return id;
    }
}

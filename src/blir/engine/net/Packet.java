package blir.engine.net;

/**
 *
 * @author Blir
 */
public abstract class Packet {
    
    public final int id;
    
    public Packet(int id) {
        this.id = id;
    }
}

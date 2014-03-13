package blir.engine.net;

import blir.engine.game.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author Blir
 */
public abstract class PacketType {

    public final int id;

    public PacketType(int id) {
        this.id = id;
    }
    
    public abstract void read(DataInputStream in);
    
    public abstract void execute(Game game);
    
    public abstract void write(Packet packet, DataOutputStream out);
}

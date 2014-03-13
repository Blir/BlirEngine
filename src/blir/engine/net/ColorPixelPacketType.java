package blir.engine.net;

import blir.engine.game.Game;

import java.io.DataInputStream;
import java.io.DataOutputStream;

/**
 *
 * @author Blir
 */
public class ColorPixelPacketType extends PacketType {

    public ColorPixelPacketType(int id) {
        super(id);
    }

    @Override
    public void read(DataInputStream in) {
    }

    @Override
    public void execute(Game game) {
    }

    @Override
    public void write(Packet packet, DataOutputStream out) {
    }
}

package blir.engine.game;

import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
import blir.engine.net.Packet;
import blir.engine.net.PacketType;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.logging.Level;

import blir.engine.item.Item;
import blir.engine.util.Location;

/**
 *
 * @author Blir
 */
public abstract class MultiplayerGameServer extends Game {

    final Map<Integer, PacketType> packetTypes = new HashMap<>();
    final List<GameClient> clients = new LinkedList<>();
    final Map<Integer, EntityType> entityTypes = new HashMap<>();
    final Map<Integer, Item> itemTypes = new HashMap<>();

    final int PIXELS;
    
    GameState state;
    final Map<EntityType, Set<Location>> entityLocations = new HashMap<>();
    Entity[][] thisTick;
    Entity[][] nextTick;
    int tick;
    int speed;

    ServerSocket socket;
    boolean open;

    public MultiplayerGameServer(String name, int pixels) {
        super(name);
        this.PIXELS = pixels;
        thisTick = new Entity[pixels][pixels];
    }

    public Thread open(int port, int timeout)
            throws IOException {
        socket = new ServerSocket(port);
        socket.setSoTimeout(timeout);
        Thread t = new Thread(new ConnectionAcceptionTask());
        t.start();
        return t;
    }

    public void reset() {
        thisTick = new Entity[PIXELS][PIXELS];
    }

    private class ConnectionAcceptionTask implements Runnable {

        @Override
        public void run() {
            Socket clientSocket;
            GameClient client;
            while (open) {
                try {
                    clientSocket = socket.accept();
                    client = new GameClient(clientSocket);
                    clients.add(client);
                } catch (SocketTimeoutException ex) {
                    // ignore
                } catch (IOException ex) {
                    log(Level.FINER, "Error accepting a connection", ex);
                }
            }
        }
    }

    private class GameClient implements Runnable {

        final Socket socket;
        final DataInputStream in;
        final DataOutputStream out;

        boolean open;

        GameClient(Socket socket)
                throws IOException {
            this.socket = socket;
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
        }

        @Override
        public void run() {
            while (open) {
                try {
                    int packetID = in.readInt();
                    PacketType type = packetTypes.get(packetID);
                    type.read(in);
                    type.execute(MultiplayerGameServer.this);
                } catch (IOException ex) {
                    log(Level.SEVERE, "Error reading packet", ex);
                }
            }
        }

        synchronized void sendPacket(Packet packet) throws IOException {
            if (open) {
                out.writeInt(packet.id);
                packetTypes.get(packet.id).write(packet, out);
            }
        }
    }
}

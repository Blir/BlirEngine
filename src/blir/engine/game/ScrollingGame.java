package blir.engine.game;

import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
import blir.engine.swing.ColorPixel;
import blir.engine.swing.Pixel;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.HashSet;
import java.util.logging.Level;

import static blir.engine.entity.EntityType.player;

/**
 *
 * @author Blir
 */
public class ScrollingGame extends Game {

    public ScrollingGame(String name, int spawnInit, int pixelSize) {
        super(name, spawnInit, pixelSize);
        thisTick = new Entity[50][250];
        speed = 50;
    }

    @Override
    public void init() {
        gui.disableIO();
        gui.disableSpawning();
        gui.disableSpeedChanging();
        registerEntityType(player);
        //registerEntityType(EntityType.ground);
        registerEntityType(EntityType.enemy);
        registerEntityType(EntityType.missile);
        //thisTick[25][25] = new Entity(player.id);
        player.setPos(25);
        generateTerrain();
        gui.setVisible(true);
    }

    @Override
    public void reset() {
        thisTick = new Entity[50][250];
        //thisTick[25][25] = new Entity(player.id);
    }

    @Override
    public int size() {
        return 50;
    }

    @Override
    public Pixel[][] getDisplay() {
        Pixel[][] pixels = new Pixel[50][50];
        for (int row = 0; row < thisTick.length; row++) {
            for (int col = player.getPos() - 25; col < player.getPos() + 25; col++) {
                //log(Level.FINEST, "col:%d playerPos:%d", col, player.getPos());
                if (isInBounds(col)) {
                    pixels[row][col - (player.getPos() - 25)] = thisTick[row][col] == null
                                                                ? new ColorPixel(row, col, PIXEL_SIZE, Color.BLACK)
                                                                : getEntityType(thisTick[row][col].id).getPixel(row, col, PIXEL_SIZE);
                }
            }
        }
        return pixels;
    }

    @Override
    public void run() {
        try {
            while (gui.isRunning()) {

                entityLocations.clear();
                for (EntityType type : entityTypes.values()) {
                    entityLocations.put(type, new HashSet<Location>());
                }
                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null) {
                            entityLocations.get(getEntityType(thisTick[row][col].id)).add(new Location(row, col));
                        }
                    }
                }

                nextTick = new Entity[50][250];

                for (int row = 0; row < thisTick.length; row++) {
                    System.arraycopy(thisTick[row], 0, nextTick[row], 0, thisTick[row].length);
                }

                state = GameState.MOVE_TICK;

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null) {
                            EntityType type = entityTypes.get(thisTick[row][col].id);
                            //type.onMoveTick(row, col, this);
                        }
                    }
                }

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null && !thisTick[row][col].isAlive()) {
                            nextTick[row][col] = null;
                        }
                    }
                }

                state = GameState.SPAWN_TICK;

                for (EntityType type : entityTypes.values()) {
                    type.onSpawnTick(this);
                }

                state = GameState.COMBAT_TICK;

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null) {
                            //entityTypes.get(thisTick[row][col].getID())
                            //.onCombatTick(row, col, this);
                        }
                    }
                }

                state = null;

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null && !thisTick[row][col].isAlive()) {
                            nextTick[row][col] = null;
                        }
                        if (nextTick[row][col] != null) {
                            nextTick[row][col].tick();
                        }
                    }
                }

                tick++;
                thisTick = nextTick;
                gui.repaint();
                updateScoreboard();
                Thread.sleep(speed);
            }
        } catch (InterruptedException ex) {
            log(Level.SEVERE, "Whoops!", ex);
        }
    }

    public void generateTerrain() {
        for (int row = 26; row < thisTick.length; row++) {
            for (int col = 0; col < thisTick[row].length; col++) {
                //thisTick[row][col] = new Entity(EntityType.ground.id);
            }
        }
    }
}

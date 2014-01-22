package blir.engine.game;

import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
import blir.engine.swing.ColorPixel;
import blir.engine.swing.Pixel;
import blir.engine.util.Location;

import java.awt.Color;
import java.util.*;
import java.util.logging.Level;

/**
 *
 * @author Travis
 */
public abstract class GenericGame extends Game {

    final int PIXELS;
    
    public GenericGame(String name, int spawnInit, int pixels, int pixelSize) {
        super(name, spawnInit, pixelSize);
        this.PIXELS = pixels;
        thisTick = new Entity[pixels][pixels];
        
        int size = PIXEL_SIZE * pixels + 75;
        gui.setSize(size, size);
        gui.setLocationRelativeTo(null);
    }

    @Override
    public void reset() {
        thisTick = new Entity[PIXELS][PIXELS];
    }

    @Override
    public int size() {
        return PIXELS;
    }

    @Override
    public Pixel[][] getDisplay() {
        Pixel[][] display = new Pixel[PIXELS][PIXELS];
        for (int row = 0; row < PIXELS; row++) {
            for (int col = 0; col < PIXELS; col++) {
                display[row][col] = thisTick[row][col] == null
                                   ? new ColorPixel(row, col, PIXEL_SIZE, Color.BLACK)
                                   : getEntityType(thisTick[row][col].id).getPixel(row, col, PIXEL_SIZE);
            }
        }
        return display;
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

                nextTick = new Entity[PIXELS][PIXELS];

                for (int row = 0; row < thisTick.length; row++) {
                    System.arraycopy(thisTick[row], 0, nextTick[row], 0, thisTick[row].length);
                }

                state = GameState.MOVE_TICK;

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null) {
                            thisTick[row][col].onMoveTick(row, col, this);
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
                            thisTick[row][col].onCombatTick(row, col, this);
                        }
                    }
                }

                state = null;

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null
                            && (!thisTick[row][col].isAlive()
                                || (thisTick[row][col].getX() != row
                                    || thisTick[row][col].getY() != col))) {

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
}

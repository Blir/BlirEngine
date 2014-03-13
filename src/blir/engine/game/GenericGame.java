package blir.engine.game;

import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
import blir.engine.swing.Pixel;
import blir.engine.swing.PixelType;
import blir.engine.util.Location;

import java.util.*;
import java.util.logging.Level;

/**
 *
 * @author Travis
 */
public abstract class GenericGame extends SinglePlayerGame {

    public GenericGame(String name, int spawnInit, int pixels, int pixelSize) {
        super(name, spawnInit, pixelSize, pixels);
        thisTick = new Entity[pixels][pixels];
        gui.setSize(RECOMMENDED_WINDOW_SIZE, RECOMMENDED_WINDOW_SIZE);
        gui.setLocationRelativeTo(null);
    }

    @Override
    public void reset() {
        thisTick = new Entity[pixels][pixels];
        animations.clear();
    }

    @Override
    public int size() {
        return pixels;
    }

    @Override
    public Pixel[][] getDisplay() {
        Pixel[][] display = new Pixel[thisTick.length][thisTick[0].length];
        for (int row = 0; row < thisTick.length; row++) {
            for (int col = 0; col < thisTick[row].length; col++) {
                display[row][col]
                        = new Pixel(row, col, thisTick[row][col] == null
                                ? PixelType.emptyPixel.id : thisTick[row][col].id);
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

                nextTick = new Entity[pixels][pixels];

                for (int row = 0; row < thisTick.length && row < nextTick.length; row++) {
                    if (thisTick.length != nextTick.length) {
                        log(Level.INFO, "[This:%d,Next:%d,This:%d,Next:%d]", thisTick.length, nextTick.length, thisTick[row].length, nextTick[row].length);
                    }
                    System.arraycopy(thisTick[row], 0, nextTick[row], 0, nextTick[row].length);
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

                for (int row = 0; row < nextTick.length; row++) {
                    for (int col = 0; col < nextTick[row].length; col++) {
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

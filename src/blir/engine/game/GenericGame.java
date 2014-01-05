package blir.engine.game;

import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
import blir.engine.util.Location;

import java.util.*;
import java.util.logging.Level;

/**
 *
 * @author Travis
 */
public abstract class GenericGame extends Game {

    public GenericGame(String name, int spawnInit, int pixelSize) {
        super(name, spawnInit, pixelSize);
        thisTick = new Entity[50][50];
    }

    @Override
    public void reset() {
        thisTick = new Entity[50][50];
    }

    @Override
    public int size() {
        return 50;
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
                            entityLocations.get(getEntityTypeByID(thisTick[row][col].getID())).add(new Location(row, col));
                        }
                    }
                }

                nextTick = new Entity[50][50];

                for (int row = 0; row < thisTick.length; row++) {
                    System.arraycopy(thisTick[row], 0, nextTick[row], 0, thisTick[row].length);
                }

                state = GameState.MOVE_TICK;

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null) {
                            EntityType type = entityTypes.get(thisTick[row][col].getID());
                            type.onMoveTick(row, col, this);
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
                            entityTypes.get(thisTick[row][col].getID())
                                    .onCombatTick(row, col, this);
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

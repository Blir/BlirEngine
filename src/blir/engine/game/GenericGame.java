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

    public GenericGame(String name, int spawnInit) {
        super(name, spawnInit);
        thisTick = new Entity[50][50];
    }
    
    @Override
    public void reset() {
        thisTick = new Entity[50][50];
    }

    @Override
    public void run() {
        try {
            while (gui.isRunning()) {

                nextTick = new Entity[50][50];

                for (int row = 0; row < thisTick.length; row++) {
                    System.arraycopy(thisTick[row], 0, nextTick[row], 0, thisTick[row].length);
                }

                Map<EntityType, List<Location>> entityLocations = new HashMap<>();
                for (EntityType type : entityTypes.values()) {
                    entityLocations.put(type, new LinkedList<Location>());
                }

                state = GameState.MOVE_TICK;

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null) {
                            EntityType type = entityTypes.get(thisTick[row][col].id);
                            type.onMoveTick(row, col, this);
                            entityLocations.get(type).add(new Location(row, col));
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
                    type.onSpawnTick(entityLocations.get(type), this);
                }

                state = GameState.COMBAT_TICK;

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null) {
                            entityTypes.get(thisTick[row][col].id)
                                    .onCombatTick(row, col, this);
                        }
                    }
                }

                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null) {
                            if (thisTick[row][col].isAlive()) {
                                nextTick[row][col].tick();
                            } else {
                                nextTick[row][col] = null;
                            }
                        }
                    }
                }

                state = null;

                thisTick = nextTick;
                gui.repaint();
                Thread.sleep(speed);
            }
        } catch (InterruptedException ex) {
            log(Level.SEVERE, "Whoops!", ex);
        }
    }
}

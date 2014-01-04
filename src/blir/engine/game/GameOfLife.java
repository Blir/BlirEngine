package blir.engine.game;

import blir.engine.slot.Entity;
import blir.engine.slot.EntityType;
import java.util.logging.Level;

/**
 *
 * @author Travis
 */
public class GameOfLife extends Game {

    public GameOfLife() {
        super("Game Of Life");
        thisTick = new Entity[50][50];
    }

    @Override
    public void init() {
        gui.setVisible(true);
        registerEntityType(EntityType.original);
    }

    @Override
    public void run() {
        try {
            while (gui.isRunning()) {
                nextTick = new Entity[50][50];
                // MOVE TICK
                for (int row = 0; row < thisTick.length; row++) {
                    for (int col = 0; col < thisTick[row].length; col++) {
                        if (thisTick[row][col] != null) {
                            entityTypes.get(thisTick[row][col].id)
                                    .onMoveTick(row, col, this);
                        } else {
                            
                        }
                    }
                }
                // SPAWN TICK
                // COMBAT TICK
                thisTick = nextTick;
                gui.repaint();
                Thread.sleep(50);
            }
        } catch (InterruptedException ex) {
            log(Level.SEVERE, "Whoops!", ex);
        }
    }
}

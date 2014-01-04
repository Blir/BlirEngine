package blir.engine.game;

import java.util.logging.Level;

/**
 *
 * @author Travis
 */
public class GameOfLife extends Game {

    public GameOfLife() {
        super("Game Of Life");
    }

    @Override
    public void init() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void run() {
        try {
            while (gui.isRunning()) {
                for (int row = 0; row < slots.length; row++) {
                    for (int col = 0; col < slots[row].length; col++) {
                        slotTypes.get(slots[row][col].id).onUpdate(row, col);
                    }
                }
                Thread.sleep(50);
            }
        } catch (InterruptedException ex) {
            log(Level.SEVERE, "Whoops!", ex);
        }
    }
}

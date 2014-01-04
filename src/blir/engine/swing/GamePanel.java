package blir.engine.swing;

import blir.engine.game.Game;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

/**
 *
 * @author Blir
 */
public class GamePanel extends JPanel {

    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int row = 0; row < game.pixels(); row++) {
            for (int col = 0; col < game.pixels(); col++) {
                g.setColor(game.getEntityAt(row, col) != null
                           ? game.getEntityTypeByID(game.getEntityAt(row, col).id).color
                           : Color.BLACK);
                g.fillRect(15 * col, 15 * row, 14, 14);
            }
        }
    }
}

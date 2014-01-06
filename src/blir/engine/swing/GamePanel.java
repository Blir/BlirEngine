package blir.engine.swing;

import blir.engine.game.Game;
import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
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
        Pixel[][] pixels = game.getDisplay();
        for (Pixel[] row : pixels) {
            for (Pixel pixel : row) {
                pixel.draw(g);
            }
        }
    }
}

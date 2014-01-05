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
        for (int row = 0; row < game.pixels(); row++) {
            for (int col = 0; col < game.pixels(); col++) {
                Entity entity = game.getEntityAt(row, col);
                if (entity == null) {
                    g.setColor(Color.BLACK);
                    g.fillRect(15 * col, 15 * row, 14, 14);
                } else {
                    EntityType type = game.getEntityTypeByID(entity.getID());
                    if (type.color != null) {
                        g.setColor(type.color);
                        g.fillRect(15 * col, 15 * row, 14, 14);
                    } else if (type.img != null) {
                        g.drawImage(type.img, 15 * col, 15 * row, this);
                    }
                }
            }
        }
    }
}

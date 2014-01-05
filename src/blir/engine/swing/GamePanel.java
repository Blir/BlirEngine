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
        for (int row = 0; row < game.size(); row++) {
            for (int col = 0; col < game.size(); col++) {
                Entity entity = game.getEntityAt(row, col);
                if (entity == null) {
                    g.setColor(Color.BLACK);
                    g.fillRect(game.PIXEL_SIZE * col, game.PIXEL_SIZE * row, game.PIXEL_SIZE - 1, game.PIXEL_SIZE - 1);
                } else {
                    EntityType type = game.getEntityTypeByID(entity.getID());
                    if (type.color != null) {
                        g.setColor(type.color);
                        g.fillRect(game.PIXEL_SIZE * col, game.PIXEL_SIZE * row, game.PIXEL_SIZE - 1, game.PIXEL_SIZE - 1);
                    } else if (type.img != null) {
                        g.drawImage(type.img, game.PIXEL_SIZE * col, game.PIXEL_SIZE * row, this);
                    }
                }
            }
        }
    }
}

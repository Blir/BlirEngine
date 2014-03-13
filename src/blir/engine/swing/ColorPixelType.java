package blir.engine.swing;

import blir.engine.game.SinglePlayerGame;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Blir
 */
public class ColorPixelType extends PixelType {

    final Color color;
    
    public ColorPixelType(int id, Color color) {
        super(id);
        this.color = color;
    }

    @Override
    public void draw(Pixel pixel, SinglePlayerGame game, Graphics g) {
        g.setColor(color);
        g.fillRect(game.getPixelSize() * pixel.x, game.getPixelSize() * pixel.y, game.getPixelSize(), game.getPixelSize());
    }
}

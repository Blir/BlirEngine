package blir.engine.swing;

import blir.engine.entity.MortalEntity;
import blir.engine.game.SinglePlayerGame;
import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Blir
 */
public class HealthColorPixelType extends ColorPixelType {

    public HealthColorPixelType(int id, Color color) {
        super(id, color);
    }

    @Override
    public void draw(Pixel pixel, SinglePlayerGame game, Graphics g) {
        MortalEntity entity = (MortalEntity) game.getEntity(pixel.x, pixel.y);
        if (entity != null) {
            float[] hsb = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
            Color c = Color.getHSBColor(hsb[0], hsb[1],
                                        (float) entity.getHealth() / entity.getMaxHP() * hsb[2]);
            g.setColor(c);
            g.fillRect(game.getPixelSize() * pixel.x, game.getPixelSize() * pixel.y, game.getPixelSize(), game.getPixelSize());
        }
    }
}

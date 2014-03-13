package blir.engine.swing;

import blir.engine.game.SinglePlayerGame;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Blir
 */
public class ImagePixelType extends PixelType {

    final BufferedImage img;
    
    public ImagePixelType(int id, File path) throws IOException {
        super(id);
        img = ImageIO.read(path);
    }

    @Override
    public void draw(Pixel pixel, SinglePlayerGame game, Graphics g) {
        g.drawImage(img, game.getPixelSize() * pixel.x, game.getPixelSize() * pixel.y, null);
    }
}

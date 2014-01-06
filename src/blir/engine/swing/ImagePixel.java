package blir.engine.swing;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

/**
 *
 * @author Blir
 */
public class ImagePixel implements Pixel {

    private final int x, y, size;
    private final BufferedImage img;

    public ImagePixel(int x, int y, int size, BufferedImage img) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.img = img;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(img, size * y, size * x, null);
    }
}

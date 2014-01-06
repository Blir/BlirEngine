package blir.engine.swing;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Blir
 */
public class ColorPixel implements Pixel {

    private final int x, y, size;
    private final Color color;

    public ColorPixel(int x, int y, int size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.fillRect(size * y, size * x, size - 1, size - 1);
    }
}

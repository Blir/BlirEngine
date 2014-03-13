package blir.engine.swing;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author Blir
 */
public class HitAnimation extends Animation {

    final int hit;
    final int x, y;
    int size;
    Color color;
    
    public HitAnimation(int hit, int x, int y, Color c) {
        this.hit = hit;
        size = hit;
        this.x = x;
        this.y = y;
        color = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());
    }
    
    @Override
    public void draw(Graphics g) {
        g.setColor(color);
        g.drawOval(x, y, size, size);
        size -= hit / 4 + 1;
        alive = size > 0;
    }
}

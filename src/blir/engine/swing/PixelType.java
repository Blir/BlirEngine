package blir.engine.swing;

import blir.engine.game.SinglePlayerGame;
import java.awt.Color;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Blir
 */
public abstract class PixelType {
    
    public static final PixelType emptyPixel;
    
    private static final Map<Color, ColorPixelType> colorPixels = new HashMap<>();
    private static final Map<Color, HealthColorPixelType> healthColorPixels = new HashMap<>();
    
    static {
        emptyPixel = colorPixelTypeFor(Color.BLACK, -1);
    }
    
    public static ColorPixelType colorPixelTypeFor(Color color, int id) {
        ColorPixelType type = colorPixels.get(color);
        if (type == null) {
            type = new ColorPixelType(id, color);
            colorPixels.put(color, type);
        }
        return type;
    }
    
    public static HealthColorPixelType healthColorPixelTypeFor(Color color, int id) {
        HealthColorPixelType type = healthColorPixels.get(color);
        if (type == null) {
            type = new HealthColorPixelType(id, color);
            healthColorPixels.put(color, type);
        }
        return type;
    }
    
    public final int id;
    
    public PixelType(int id) {
        this.id = id;
    }
    
    public abstract void draw(Pixel pixel, SinglePlayerGame game, Graphics g);
}

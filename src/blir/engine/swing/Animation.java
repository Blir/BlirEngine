package blir.engine.swing;

import blir.engine.entity.Entity;
import blir.engine.game.SinglePlayerGame;

import java.awt.Graphics;

/**
 *
 * @author Blir
 */
public abstract class Animation {

    public static HitAnimation hitAnimationFor(int hit, Entity entity,
                                               SinglePlayerGame game) {
        return new HitAnimation(4 * hit, game.getPixelSize() * entity.getX(), game.getPixelSize() * entity.getY(),
                                ((ColorPixelType) game.getPixelType(entity.id)).color);
    }

    boolean alive = true;

    public abstract void draw(Graphics g);

    public final boolean isAlive() {
        return alive;
    }
}

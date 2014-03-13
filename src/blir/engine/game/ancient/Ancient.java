package blir.engine.game.ancient;

import blir.engine.game.SinglePlayerGame;
import blir.engine.swing.Pixel;

/**
 *
 * @author Blir
 */
public class Ancient extends SinglePlayerGame {

    public Ancient() {
        super("Ancient", 0, 15, 50);
    }

    @Override
    public void reset() {}

    @Override
    public int size() {return getMapSize();}

    @Override
    public Pixel[][] getDisplay() {return null;}

    @Override
    public void init() {}

    @Override
    public void run() {}
}

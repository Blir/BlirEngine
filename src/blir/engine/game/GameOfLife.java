package blir.engine.game;

import blir.engine.entity.EntityType;
import blir.engine.swing.PixelType;
import java.awt.Color;

/**
 *
 * @author Blir
 */
public class GameOfLife extends GenericGame {

    public GameOfLife() {
        super("Game Of Life", EntityType.original.id, 50, 15);
        speed = 200;
    }

    @Override
    public void init() {
        registerEntityType(EntityType.original);
        registerPixelType(PixelType.colorPixelTypeFor(Color.YELLOW, EntityType.original.id));
        gui.setVisible(true);
    }
}

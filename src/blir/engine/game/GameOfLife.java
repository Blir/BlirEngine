package blir.engine.game;

import blir.engine.entity.EntityType;

/**
 *
 * @author Blir
 */
public class GameOfLife extends GenericGame {

    public GameOfLife() {
        super("Game Of Life", EntityType.original.id, 15);
        speed = 200;
    }

    @Override
    public void init() {
        registerEntityType(EntityType.original);
        gui.setVisible(true);
    }
}

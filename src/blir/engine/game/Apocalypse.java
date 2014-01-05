package blir.engine.game;

import blir.engine.entity.EntityType;

/**
 *
 * @author Blir
 */
public class Apocalypse extends GenericGame {

    public Apocalypse() {
        super("Apocalypse", EntityType.wall.id);
        speed = 150;
    }

    @Override
    public void init() {
        registerEntityType(EntityType.wall);
        registerEntityType(EntityType.human);
        registerEntityType(EntityType.zombie);
        gui.setVisible(true);
    }
}

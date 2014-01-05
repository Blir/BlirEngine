package blir.engine.game;

import blir.engine.entity.EntityType;

/**
 *
 * @author Blir
 */
public class ArchIorZard extends GenericGame {

    public ArchIorZard() {
        super("ArchIorZard", EntityType.wall.id);
        speed = 150;
    }

    @Override
    public void init() {
        registerEntityType(EntityType.wall);
        registerEntityType(EntityType.archer);
        registerEntityType(EntityType.warrior);
        registerEntityType(EntityType.wizard);
        gui.setVisible(true);
    }
}

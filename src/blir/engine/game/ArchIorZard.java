package blir.engine.game;

import blir.engine.entity.EntityType;

/**
 *
 * @author Blir
 */
public class ArchIorZard extends GenericGame {

    public ArchIorZard() {
        super("ArchIorZard", EntityType.wall.id, 15);
        speed = 150;
    }

    @Override
    public void init() {
        registerEntityType(EntityType.wall);
        registerEntityType(EntityType.archer);
        registerEntityType(EntityType.warrior);
        registerEntityType(EntityType.wizard);
        scoreboard.addTeam(EntityType.archer);
        scoreboard.addTeam(EntityType.warrior);
        scoreboard.addTeam(EntityType.wizard);
        gui.setVisible(true);
    }
}

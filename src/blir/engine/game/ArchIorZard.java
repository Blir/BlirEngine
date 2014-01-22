package blir.engine.game;

import blir.engine.entity.EntityType;

/**
 *
 * @author Blir
 */
public class ArchIorZard extends GenericGame {

    public final Alliance archers = new Alliance("Archers");
    public final Alliance warriors = new Alliance("Warriors");
    public final Alliance wizards = new Alliance("Wizards");

    public ArchIorZard() {
        super("ArchIorZard", EntityType.wall.id, 50, 15);
        speed = 150;
    }

    @Override
    public void init() {
        registerEntityType(EntityType.wall);
        registerEntityType(EntityType.archer);
        registerEntityType(EntityType.warrior);
        registerEntityType(EntityType.wizard);
        archers.add(EntityType.archer);
        warriors.add(EntityType.warrior);
        wizards.add(EntityType.wizard);
        scoreboard.add(archers);
        scoreboard.add(warriors);
        scoreboard.add(wizards);
        gui.setVisible(true);
    }
}

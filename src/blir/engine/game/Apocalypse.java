package blir.engine.game;

import blir.engine.entity.EntityType;
import blir.engine.item.Item;

/**
 *
 * @author Blir
 */
public class Apocalypse extends GenericGame {

    public Apocalypse() {
        super("Apocalypse", EntityType.wall.id, 15);
        speed = 150;
    }

    @Override
    public void init() {
        registerEntityType(EntityType.wall);
        registerEntityType(EntityType.human);
        registerEntityType(EntityType.zombie);
        registerItemType(Item.wall);
        scoreboard.addTeam(EntityType.human);
        scoreboard.addTeam(EntityType.zombie);
        gui.setVisible(true);
    }
}

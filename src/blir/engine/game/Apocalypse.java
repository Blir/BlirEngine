package blir.engine.game;

import blir.engine.entity.EntityType;
import blir.engine.item.Item;

/**
 *
 * @author Blir
 */
public class Apocalypse extends GenericGame {

    public final Alliance humans = new Alliance("Humans");
    public final Alliance zombies = new Alliance("Zombies");

    public Apocalypse() {
        super("Apocalypse", EntityType.wall.id, 50, 15);
        speed = 150;
    }

    @Override
    public void init() {
        registerEntityType(EntityType.wall);
        registerEntityType(EntityType.human);
        registerEntityType(EntityType.zombie);
        registerEntityType(EntityType.juggernaut);
        registerItemType(Item.wall);
        humans.add(EntityType.human);
        humans.add(EntityType.juggernaut);
        zombies.add(EntityType.zombie);
        scoreboard.add(humans);
        scoreboard.add(zombies);
        gui.setVisible(true);
    }
}

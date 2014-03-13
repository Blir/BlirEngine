package blir.engine.game;

import blir.engine.entity.EntityType;
import blir.engine.item.Item;
import blir.engine.swing.PixelType;

import java.awt.Color;

import static blir.engine.entity.EntityType.*;

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
        registerPixelType(PixelType.healthColorPixelTypeFor(Color.YELLOW, wall.id));
        registerPixelType(PixelType.healthColorPixelTypeFor(Color.GREEN, zombie.id));
        registerPixelType(PixelType.healthColorPixelTypeFor(Color.BLUE, human.id));
        registerPixelType(PixelType.colorPixelTypeFor(Color.CYAN, human.spawner.id));
        registerPixelType(PixelType.healthColorPixelTypeFor(Color.RED, juggernaut.id));
        registerPixelType(PixelType.colorPixelTypeFor(Color.DARK_GRAY, zombie.spawner.id));
        registerPixelType(PixelType.colorPixelTypeFor(Color.PINK, juggernaut.spawner.id));
        registerItemType(Item.wall);
        humans.add(EntityType.human);
        humans.add(EntityType.juggernaut);
        zombies.add(EntityType.zombie);
        scoreboard.add(humans);
        scoreboard.add(zombies);
        gui.setVisible(true);
    }
}

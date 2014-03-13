package blir.engine.game;

import blir.engine.entity.EntityType;
import blir.engine.swing.PixelType;
import java.awt.Color;

import static blir.engine.entity.EntityType.*;

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
        registerEntityType(wall);
        registerEntityType(archer);
        registerEntityType(warrior);
        registerEntityType(wizard);
        registerPixelType(PixelType.colorPixelTypeFor(Color.YELLOW, wall.id));
        registerPixelType(PixelType.colorPixelTypeFor(Color.GREEN, archer.id));
        registerPixelType(PixelType.colorPixelTypeFor(Color.RED, warrior.id));
        registerPixelType(PixelType.colorPixelTypeFor(Color.BLUE, wizard.id));
        registerPixelType(PixelType.colorPixelTypeFor(Color.MAGENTA, archer.spawner.id));
        registerPixelType(PixelType.colorPixelTypeFor(Color.ORANGE, warrior.spawner.id));
        registerPixelType(PixelType.colorPixelTypeFor(Color.CYAN, wizard.spawner.id));
        archers.add(archer);
        warriors.add(warrior);
        wizards.add(wizard);
        scoreboard.add(archers);
        scoreboard.add(warriors);
        scoreboard.add(wizards);
        gui.setVisible(true);
    }
}

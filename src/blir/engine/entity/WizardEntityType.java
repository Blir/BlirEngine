package blir.engine.entity;

import blir.engine.game.SinglePlayerGame;
import blir.engine.game.Team;

import java.awt.Color;

/**
 *
 * @author Blir
 */
public class WizardEntityType extends EntityType implements Team {

    int damageDealt;

    public WizardEntityType(int id, int spawnerID) {
        super(id, "Wizard", Color.BLUE, new EntitySpawner(spawnerID, "Wizard Spawner", Color.CYAN, 25, 10, id));
    }

    @Override
    public Entity spawn() {
        return new Wizard();
    }

    @Override
    public void init(SinglePlayerGame game) {
    }

    @Override
    public void onSpawnTick(SinglePlayerGame game) {
    }

    @Override
    public int getScore() {
        return damageDealt;
    }
}

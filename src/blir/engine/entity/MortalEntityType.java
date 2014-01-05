package blir.engine.entity;

import blir.engine.game.Game;
import java.awt.Color;
import java.awt.image.BufferedImage;

/**
 *
 * @author Blir
 */
public abstract class MortalEntityType extends EntityType {

    public final int maxHP;

    public MortalEntityType(int id, String name, Color color,
                            EntitySpawner spawner, int maxHP) {
        this(id, name, color, null, spawner, maxHP);
    }

    public MortalEntityType(int id, String name, BufferedImage img,
                            EntitySpawner spawner, int maxHP) {
        this(id, name, null, img, spawner, maxHP);
    }

    public MortalEntityType(int id, String name, Color color, int maxHP) {
        this(id, name, color, null, null, maxHP);
    }

    public MortalEntityType(int id, String name, BufferedImage img, int maxHP) {
        this(id, name, null, img, null, maxHP);
    }

    MortalEntityType(int id, String name, Color color, BufferedImage img,
                     EntitySpawner spawner, int maxHP) {
        super(id, name, color, img, spawner);
        this.maxHP = maxHP;
    }
    
    @Override
    public void onCombatTick(int x, int y, Game game) {
        Entity entity = game.getEntityAt(x, y);
        entity.setAlive(entity.getDamage() < maxHP);
    }
}

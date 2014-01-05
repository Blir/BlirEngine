package blir.engine.entity;

import blir.engine.game.Game;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 *
 * @author Blir
 */
public abstract class CombatEntityType extends MortalEntityType {

    Map<Integer, Integer> damageMap;

    public CombatEntityType(int id, String name, Color color,
                            EntitySpawner spawner, int maxHP) {
        this(id, name, color, null, spawner, maxHP);
    }

    public CombatEntityType(int id, String name, BufferedImage img,
                            EntitySpawner spawner, int maxHP) {
        this(id, name, null, img, spawner, maxHP);
    }

    public CombatEntityType(int id, String name, Color color, int maxHP) {
        this(id, name, color, null, null, maxHP);
    }

    public CombatEntityType(int id, String name, BufferedImage img, int maxHP) {
        this(id, name, null, img, null, maxHP);
    }

    CombatEntityType(int id, String name, Color color, BufferedImage img,
                     EntitySpawner spawner, int maxHP) {
        super(id, name, color, img, spawner, maxHP);
    }

    @Override
    public void onCombatTick(int x, int y, Game game) {
        super.onCombatTick(x, y, game);
        damageByMap(game.getSquareNeighbors(x, y, 1), damageMap, game);
    }
}

package blir.engine.game.ancient;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 *
 * @author Blir
 */
public class AncientPlayer implements MouseListener {

    public final String name;

    private int level, xp;
    private int attack, defence;
    private int gold;
    private int luck;

    public AncientPlayer(String name) {
        this.name = name;
    }

    public int addXP(int xp) {
        return this.xp += xp;
    }

    public int increaseAttack(int increase) {
        return attack += increase;
    }

    public int increaseDefence(int increase) {
        return defence += increase;
    }

    public int addGold(int gold) {
        return this.gold += gold;
    }

    public int removeGold(int gold) {
        return this.gold -= gold >= 0 ? this.gold : (this.gold = 0);
    }

    public int increaseLuck(int increase) {
        return luck += increase;
    }

    public int getLevel() {
        return level;
    }

    public int getXP() {
        return xp;
    }

    public int getAttack() {
        return attack;
    }

    public int getDefence() {
        return defence;
    }

    public int getGold() {
        return gold;
    }

    public int getLuck() {
        return luck;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}

package blir.engine.entity;

/**
 *
 * @author Blir
 */
public abstract class MortalEntity extends Entity {

    int maxHP;
    int dmg;

    public MortalEntity(int id, int maxHP) {
        super(id);
        this.maxHP = maxHP;
    }

    public int getDamage() {
        return dmg;
    }

    public void setDamage(int dmg) {
        this.dmg = dmg;
    }

    public void damage(int amount) {
        this.dmg += amount;
    }

    public void heal(int amount) {
        this.dmg -= amount;
    }

    @Override
    public boolean isAlive() {
        return dmg < maxHP;
    }

    @Override
    public boolean setAlive(boolean alive) {
        dmg = alive ? 0 : maxHP;
        return alive;
    }
}

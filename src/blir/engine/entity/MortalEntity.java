package blir.engine.entity;

/**
 *
 * @author Blir
 */
public abstract class MortalEntity extends Entity {

    final int maxHP;
    int dmg;

    public MortalEntity(int id, int maxHP) {
        super(id);
        this.maxHP = maxHP;
    }

    public int getDamage() {
        return dmg;
    }
    
    public int getHealth() {
        return maxHP - dmg;
    }
    
    public int getMaxHP() {
        return maxHP;
    }

    public void setDamage(int dmg) {
        this.dmg = dmg;
    }

    public boolean damage(int amount) {
        int temp = dmg;
        this.dmg += amount;
        return temp < maxHP && !isAlive();
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

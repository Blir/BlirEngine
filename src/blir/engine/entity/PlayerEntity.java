package blir.engine.entity;

import blir.engine.game.Game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Blir
 */
public class PlayerEntity extends MortalEntityType implements KeyListener {

    int pos;
    
    public PlayerEntity(int id) {
        super(id, "Player", Color.YELLOW, 100);
    }

    @Override
    public void init(Game game) {
        game.registerKeyListener(this);
    }

    @Override
    public void entityInit(Entity entity) {
    }

    @Override
    public void onMoveTick(int x, int y, Game game) {
        pos = x;
    }

    @Override
    public void onSpawnTick(Game game) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_SPACE:
                break;
            case KeyEvent.VK_RIGHT:
                break;
            case KeyEvent.VK_LEFT:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    public void setPos(int pos) {
        this.pos = pos;
    }
    
    public int getPos() {
        return pos;
    }
}

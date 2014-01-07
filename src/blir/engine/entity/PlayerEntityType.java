package blir.engine.entity;

import blir.engine.game.Game;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 *
 * @author Blir
 */
public class PlayerEntityType extends EntityType implements KeyListener {

    int pos;
    
    public PlayerEntityType(int id) {
        super(id, "Player", Color.YELLOW);
    }

    @Override
    public void init(Game game) {
        game.registerKeyListener(this);
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

package blir.engine.swing;

import blir.engine.game.SinglePlayerGame;

import java.awt.Graphics;
import java.awt.event.*;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Blir
 */
public class GamePanel extends JPanel implements MouseListener,
                                                 MouseMotionListener,
                                                 ComponentListener {

    private final SinglePlayerGame game;

    private int frames = 0;

    public GamePanel(SinglePlayerGame game) {
        this.game = game;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
        this.addComponentListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Pixel[][] pixels = game.getDisplay();
        for (Pixel[] row : pixels) {
            for (Pixel pixel : row) {
                game.getPixelType(pixel.id).draw(pixel, game, g);
            }
        }
        List<Animation> animations = game.getAnimations();
        
        for (Animation a : animations) {
            a.draw(g);
        }
        frames++;
    }

    public int getFrames() {
        return frames;
    }

    public void resetFrames() {
        frames = 0;
    }

    private int placeX, placeY;
    int spawnID;
    private boolean erase;

    @Override
    public void mouseClicked(MouseEvent e) {
        //if (spawnMenuItem.isEnabled()) {
        int newX = (int) Math.round(e.getX() / (double) game.getPixelSize());
        int newY = (int) Math.round(e.getY() / (double) game.getPixelSize());
        if (game.isInBounds(newX, newY)) {
            if (erase) {
                game.removeEntity(newX, newY);
            } else {
                game.placeEntity(newX, newY, spawnID);
            }
        }
        repaint();
        //}
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //if (spawnMenuItem.isEnabled()) {
        int newX = (int) Math.round(e.getX() / (double) game.getPixelSize());
        int newY = (int) Math.round(e.getY() / (double) game.getPixelSize());
        if (game.isInBounds(newY, newX)) {
            erase = game.getEntity(newX, newY) != null;
        }
        repaint();
        //}
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

    @Override
    public void mouseDragged(MouseEvent e) {
        //if (spawnMenuItem.isEnabled()) {
        int newX = (int) Math.round(e.getX() / (double) game.getPixelSize());
        int newY = (int) Math.round(e.getY() / (double) game.getPixelSize());

        if (!game.isInBounds(newX, newY) || (newX == placeX && newY == placeY)
            || (game.getEntity(newX, newY) == null == erase)) {

            return;
        }

        placeX = newX;
        placeY = newY;

        if (erase) {
            game.removeEntity(newX, newY);
        } else {
            game.placeEntity(newX, newY, spawnID);
        }
        repaint();
        //}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void componentResized(ComponentEvent e) {
        int lim = Math.min(getWidth(), getHeight());
        if (game.getMapSize() != 0) {
            int size = lim / game.getMapSize();
            game.setPixelSize(size);
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}

package blir.engine.swing;

import blir.engine.game.Game;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import javax.swing.JPanel;

/**
 *
 * @author Blir
 */
public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {

    private final Game game;

    private int frames = 0;

    public GamePanel(Game game) {
        this.game = game;
        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Pixel[][] pixels = game.getDisplay();
        for (Pixel[] row : pixels) {
            for (Pixel pixel : row) {
                pixel.draw(g);
            }
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
        int newX = (int) Math.round(e.getX() / (double) game.PIXEL_SIZE);
        int newY = (int) Math.round(e.getY() / (double) game.PIXEL_SIZE);
        if (game.isInBounds(newY, newX)) {
            if (erase) {
                game.removeEntity(newY, newX);
            } else {
                game.placeEntity(newY, newX, spawnID);
            }
        }
        repaint();
        //}
    }

    @Override
    public void mousePressed(MouseEvent e) {
        //if (spawnMenuItem.isEnabled()) {
        int newX = (int) Math.round(e.getX() / (double) game.PIXEL_SIZE);
        int newY = (int) Math.round(e.getY() / (double) game.PIXEL_SIZE);
        if (game.isInBounds(newY, newX)) {
            erase = game.getEntity(newY, newX) != null;
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
        int newX = (int) Math.round(e.getX() / (double) game.PIXEL_SIZE);
        int newY = (int) Math.round(e.getY() / (double) game.PIXEL_SIZE);

        if (!game.isInBounds(newY, newX) || (newX == placeX && newY == placeY)
                || (game.getEntity(newY, newX) == null == erase)) {

            return;
        }

        placeX = newX;
        placeY = newY;

        if (erase) {
            game.removeEntity(newY, newX);
        } else {
            game.placeEntity(newY, newX, spawnID);
        }
        repaint();
        //}
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}

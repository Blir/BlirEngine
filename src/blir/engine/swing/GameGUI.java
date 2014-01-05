package blir.engine.swing;

import blir.engine.game.Game;
import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;

import java.io.*;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author Travis
 */
public class GameGUI extends javax.swing.JFrame {
    
    private final Game game;
    private final GamePanel panel;

    /**
     * Creates new form GameGUI
     *
     * @param game
     * @param spawnInit
     */
    public GameGUI(Game game, int spawnInit) {
        this.spawnID = spawnInit;
        this.panel = new GamePanel(game);
        this.game = game;
        setContentPane(panel);
        initComponents();
        setTitle(game.name);
        int size = game.PIXEL_SIZE * game.size() + 75;
        setSize(size, size);
        setLocationRelativeTo(null);
    }
    
    public boolean isRunning() {
        return toggler.getState();
    }
    
    public void disableSpawning() {
        spawnMenuItem.setEnabled(false);
        clearMenuItem.setEnabled(false);
    }
    
    public void disableSpeedChanging() {
        speedMenuItem.setEnabled(false);
    }
    
    public void disableIO() {
        saveMenuItem.setEnabled(false);
        loadMenuItem.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu2 = new javax.swing.JMenu();
        saveMenuItem = new javax.swing.JMenuItem();
        loadMenuItem = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        toggler = new javax.swing.JCheckBoxMenuItem();
        clearMenuItem = new javax.swing.JMenuItem();
        speedMenuItem = new javax.swing.JMenuItem();
        spawnMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                onMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                onMousePressed(evt);
            }
        });
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                onMouseDragged(evt);
            }
        });

        jMenu2.setText("File");

        saveMenuItem.setText("Save");
        saveMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSave(evt);
            }
        });
        jMenu2.add(saveMenuItem);

        loadMenuItem.setText("Load");
        loadMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onLoad(evt);
            }
        });
        jMenu2.add(loadMenuItem);

        jMenuBar1.add(jMenu2);

        jMenu1.setText("Game");

        toggler.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_T, java.awt.event.InputEvent.CTRL_MASK));
        toggler.setText("Toggle");
        toggler.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onToggle(evt);
            }
        });
        jMenu1.add(toggler);

        clearMenuItem.setText("Clear");
        clearMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onClear(evt);
            }
        });
        jMenu1.add(clearMenuItem);

        speedMenuItem.setText("Change Speed");
        speedMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSpeedChange(evt);
            }
        });
        jMenu1.add(speedMenuItem);

        spawnMenuItem.setText("Change Spawn");
        spawnMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onSpawnChange(evt);
            }
        });
        jMenu1.add(spawnMenuItem);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 281, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void onToggle(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onToggle
        new Thread(game).start();
    }//GEN-LAST:event_onToggle
    
    private int placeX, placeY;
    private int spawnID;
    private boolean erase;

    private void onMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onMouseClicked
        if (spawnMenuItem.isEnabled()) {
            int newX = (int) Math.round(evt.getX() / (double) game.PIXEL_SIZE - 1);
            int newY = (int) Math.round(evt.getY() / (double) game.PIXEL_SIZE - 4);
            if (game.isInBounds(newX, newY)) {
                Entity entity = new Entity(spawnID);
                game.getEntityTypeByID(spawnID).entityInit(entity);
                game.placeEntityAt(newY, newX,
                                   game.getEntityAt(newY, newX) == null
                                   ? entity
                                   : null);
            }
            repaint();
        }
    }//GEN-LAST:event_onMouseClicked

    private void onMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onMouseDragged
        if (spawnMenuItem.isEnabled()) {
            int newX = (int) Math.round(evt.getX() / (double) game.PIXEL_SIZE - 1);
            int newY = (int) Math.round(evt.getY() / (double) game.PIXEL_SIZE - 4);
            
            if (!game.isInBounds(newX, newY) || (newX == placeX && newY == placeY)
                || (game.getEntityAt(newY, newX) == null == erase)) {
                
                return;
            }
            
            placeX = newX;
            placeY = newY;
            
            Entity entity = new Entity(spawnID);
            game.getEntityTypeByID(spawnID).entityInit(entity);
            game.placeEntityAt(newY, newX, game.getEntityAt(newY, newX) == null ? entity : null);
            repaint();
        }
    }//GEN-LAST:event_onMouseDragged

    private void onMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_onMousePressed
        if (spawnMenuItem.isEnabled()) {
            int newX = (int) Math.round(evt.getX() / (double) game.PIXEL_SIZE - 1);
            int newY = (int) Math.round(evt.getY() / (double) game.PIXEL_SIZE - 4);
            if (game.isInBounds(newX, newY)) {
                erase = game.getEntityAt(newY, newX) != null;
            }
            repaint();
        }
    }//GEN-LAST:event_onMousePressed

    private void onClear(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onClear
        toggler.setState(false);
        game.reset();
        repaint();
    }//GEN-LAST:event_onClear

    private void onSpeedChange(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSpeedChange
        try {
            game.setSpeed(Integer.parseInt(JOptionPane.showInputDialog(this, "Enter speed (ms):", game.getSpeed())));
        } catch (NumberFormatException ex) {
            // ignore
        }
    }//GEN-LAST:event_onSpeedChange

    private void onSpawnChange(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSpawnChange
        new SelectorGUI<EntityType>(game.getEntityTypes()) {
            @Override
            public void onSelectionMade(EntityType selection) {
                spawnID = selection.id;
            }
        }.setVisible(true);
    }//GEN-LAST:event_onSpawnChange
    
    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();

    private void onSave(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSave
        toggler.setState(false);
        chooser.showSaveDialog(rootPane);
        if (chooser.getSelectedFile() != null) {
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(chooser.getSelectedFile()))) {
                for (int row = 0; row < game.size(); row++) {
                    for (int col = 0; col < game.size(); col++) {
                        Entity entity = game.getEntityAt(row, col);
                        if (entity != null) {
                            dos.writeInt(row);
                            dos.writeInt(col);
                            dos.writeInt(entity.getID());
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                Game.log(Level.WARNING, "File not found");
            } catch (IOException ex) {
                Game.log(Level.SEVERE, "Error saving", ex);
            }
        }
    }//GEN-LAST:event_onSave

    private void onLoad(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onLoad
        toggler.setState(false);
        chooser.showOpenDialog(rootPane);
        if (chooser.getSelectedFile() != null) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(chooser.getSelectedFile()))) {
                game.reset();
                for (;;) {
                    game.placeEntityAt(dis.readInt(), dis.readInt(), new Entity(dis.readInt()));
                }
            } catch (FileNotFoundException ex) {
                Game.log(Level.WARNING, "File not found");
            } catch (EOFException ex) {
                repaint();
            } catch (IOException ex) {
                Game.log(Level.SEVERE, "Error loading", ex);
            }
        }
    }//GEN-LAST:event_onLoad

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem clearMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem spawnMenuItem;
    private javax.swing.JMenuItem speedMenuItem;
    private javax.swing.JCheckBoxMenuItem toggler;
    // End of variables declaration//GEN-END:variables
}

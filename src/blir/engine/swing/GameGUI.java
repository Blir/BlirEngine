package blir.engine.swing;

import blir.engine.game.SinglePlayerGame;
import blir.engine.entity.Entity;
import blir.engine.entity.EntityType;
import blir.engine.game.Game;

import java.io.*;
import java.util.logging.Level;
import javax.swing.JOptionPane;

/**
 *
 * @author Travis
 */
public class GameGUI extends javax.swing.JFrame {

    private final SinglePlayerGame game;
    private final GamePanel panel;

    /**
     * Creates new form SinglePlayerGameGUI
     *
     * @param game
     * @param spawnInit
     */
    public GameGUI(SinglePlayerGame game, int spawnInit) {
        this.panel = new GamePanel(game);
        panel.spawnID = spawnInit;
        this.game = game;
        setContentPane(panel);
        initComponents();
        setTitle(game.name);
        setLocationRelativeTo(null);
    }

    public boolean isRunning() {
        return toggler.getState();
    }
    
    public void stop() {
        toggler.setState(false);
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
    
    public void disableMapSizeChanging() {
        mapSizeMenuItem.setEnabled(false);
    }
    
    public int getPanelFrames() {
        return panel.getFrames();
    }

    /**
     * This method is addAnimationlled from within the constructor to initialize the form.
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
        mapSizeMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

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

        mapSizeMenuItem.setText("Change Map Size");
        mapSizeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                onChangeMapSize(evt);
            }
        });
        jMenu1.add(mapSizeMenuItem);

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

    private void onClear(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onClear
        if (spawnMenuItem.isEnabled()) {
            toggler.setState(false);
            game.reset();
            repaint();
        }
    }//GEN-LAST:event_onClear

    private void onSpeedChange(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSpeedChange
        try {
            game.setSpeed(Integer.parseInt(JOptionPane.showInputDialog(this, "Enter speed (ms):", game.getSpeed())));
            panel.resetFrames();
            game.resetTicks();
        } catch (NumberFormatException ex) {
            // ignore
        }
    }//GEN-LAST:event_onSpeedChange

    private void onSpawnChange(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSpawnChange
        new SelectorGUI<EntityType>(game.getEntityTypes(), DISPOSE_ON_CLOSE) {
            @Override
            public void onSelectionMade(EntityType selection) {
                panel.spawnID = selection.id;
            }
        }.setVisible(true);
    }//GEN-LAST:event_onSpawnChange

    javax.swing.JFileChooser chooser = new javax.swing.JFileChooser();

    private void onSave(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onSave
        toggler.setState(false);
        chooser.showSaveDialog(this);
        if (chooser.getSelectedFile() != null) {
            try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(chooser.getSelectedFile()))) {
                for (int row = 0; row < game.size(); row++) {
                    for (int col = 0; col < game.size(); col++) {
                        Entity entity = game.getEntity(row, col);
                        if (entity != null) {
                            dos.writeInt(row);
                            dos.writeInt(col);
                            dos.writeInt(entity.id);
                        }
                    }
                }
            } catch (FileNotFoundException ex) {
                game.log(Level.WARNING, "File not found");
            } catch (IOException ex) {
                game.log(Level.SEVERE, "Error saving", ex);
            }
        }
    }//GEN-LAST:event_onSave

    private void onLoad(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onLoad
        toggler.setState(false);
        chooser.showOpenDialog(this);
        if (chooser.getSelectedFile() != null) {
            try (DataInputStream dis = new DataInputStream(new FileInputStream(chooser.getSelectedFile()))) {
                game.reset();
                for (;;) {
                    game.placeEntity(dis.readInt(), dis.readInt(), dis.readInt());
                }
            } catch (FileNotFoundException ex) {
                game.log(Level.WARNING, "File not found");
            } catch (EOFException ex) {
                repaint();
            } catch (IOException ex) {
                game.log(Level.SEVERE, "Error loading", ex);
            }
        }
    }//GEN-LAST:event_onLoad

    private void onChangeMapSize(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_onChangeMapSize
        try {
            toggler.setState(false);
            game.setMapSize(Integer.parseInt(JOptionPane.showInputDialog(this, "This will clear the map.\nEnter map size:", game.getMapSize())));
            game.reset();
            repaint();
            panel.resetFrames();
            game.resetTicks();
        } catch (NumberFormatException ex) {
            // ignore
        }
    }//GEN-LAST:event_onChangeMapSize

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem clearMenuItem;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem loadMenuItem;
    private javax.swing.JMenuItem mapSizeMenuItem;
    private javax.swing.JMenuItem saveMenuItem;
    private javax.swing.JMenuItem spawnMenuItem;
    private javax.swing.JMenuItem speedMenuItem;
    private javax.swing.JCheckBoxMenuItem toggler;
    // End of variables declaration//GEN-END:variables
}

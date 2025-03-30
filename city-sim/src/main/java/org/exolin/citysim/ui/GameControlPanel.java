package org.exolin.citysim.ui;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.exolin.citysim.World;
import org.exolin.citysim.storage.WorldStorage;

/**
 *
 * @author Thomas
 */
public class GameControlPanel extends javax.swing.JPanel
{
    private GamePanel panel;
    private final JFileChooser fileChooser = new JFileChooser(new File("./saves"));
    {
        fileChooser.setFileFilter(new FileFilter()
        {
            @Override
            public String getDescription()
            {
                return "Save files (*.cs)";
            }

            @Override
            public boolean accept(File f) {
                if (f.isDirectory()) {
                    return true;
                } else {
                    String filename = f.getName().toLowerCase();
                    return filename.endsWith(".cs");
                }
            }
         });
    }
    
    /**
     * Creates new form GameData
     */
    public GameControlPanel()
    {
        initComponents();
    }

    public void setPanel(GamePanel panel)
    {
        this.panel = panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        resetPositionLabel = new javax.swing.JLabel();
        keyLeftLabel = new javax.swing.JLabel();
        keyTopLabel = new javax.swing.JLabel();
        keyBottomLabel = new javax.swing.JLabel();
        keyRightLabel = new javax.swing.JLabel();
        zoomInLabel = new javax.swing.JLabel();
        zoomOutLabel = new javax.swing.JLabel();
        saveLabel = new javax.swing.JLabel();
        loadLabel = new javax.swing.JLabel();

        resetPositionLabel.setText("Reset position");
        resetPositionLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                resetPositionLabelMouseClicked(evt);
            }
        });

        keyLeftLabel.setText("<");
        keyLeftLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                keyLeftLabelMousePressed(evt);
            }
        });

        keyTopLabel.setText("^");
        keyTopLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                keyTopLabelMousePressed(evt);
            }
        });

        keyBottomLabel.setText("v");
        keyBottomLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                keyBottomLabelMousePressed(evt);
            }
        });

        keyRightLabel.setText(">");
        keyRightLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                keyRightLabelMousePressed(evt);
            }
        });

        zoomInLabel.setText("+");
        zoomInLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                zoomInLabelMousePressed(evt);
            }
        });

        zoomOutLabel.setText("-");
        zoomOutLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mousePressed(java.awt.event.MouseEvent evt)
            {
                zoomOutLabelMousePressed(evt);
            }
        });

        saveLabel.setText("Save");
        saveLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                saveLabelMouseClicked(evt);
            }
        });

        loadLabel.setText("Load");
        loadLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                loadLabelMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(resetPositionLabel)
                .addGap(31, 31, 31)
                .addComponent(keyLeftLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(keyTopLabel)
                    .addComponent(keyBottomLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyRightLabel)
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(zoomInLabel)
                    .addComponent(zoomOutLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 491, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(saveLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(loadLabel, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(keyTopLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(keyBottomLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(resetPositionLabel)
                                        .addComponent(keyLeftLabel))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(keyRightLabel)
                                        .addComponent(zoomInLabel))))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(saveLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(zoomOutLabel)
                                    .addComponent(loadLabel))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void resetPositionLabelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_resetPositionLabelMouseClicked
    {//GEN-HEADEREND:event_resetPositionLabelMouseClicked
        panel.resetPosition();
    }//GEN-LAST:event_resetPositionLabelMouseClicked

    private void keyLeftLabelMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_keyLeftLabelMousePressed
    {//GEN-HEADEREND:event_keyLeftLabelMousePressed
        panel.keyPressed(KeyEvent.VK_LEFT);
    }//GEN-LAST:event_keyLeftLabelMousePressed

    private void keyTopLabelMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_keyTopLabelMousePressed
    {//GEN-HEADEREND:event_keyTopLabelMousePressed
        panel.keyPressed(KeyEvent.VK_UP);
    }//GEN-LAST:event_keyTopLabelMousePressed

    private void keyBottomLabelMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_keyBottomLabelMousePressed
    {//GEN-HEADEREND:event_keyBottomLabelMousePressed
        panel.keyPressed(KeyEvent.VK_DOWN);
    }//GEN-LAST:event_keyBottomLabelMousePressed

    private void keyRightLabelMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_keyRightLabelMousePressed
    {//GEN-HEADEREND:event_keyRightLabelMousePressed
        panel.keyPressed(KeyEvent.VK_RIGHT);
    }//GEN-LAST:event_keyRightLabelMousePressed

    private void zoomInLabelMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_zoomInLabelMousePressed
    {//GEN-HEADEREND:event_zoomInLabelMousePressed
        panel.onZoom(1);
    }//GEN-LAST:event_zoomInLabelMousePressed

    private void zoomOutLabelMousePressed(java.awt.event.MouseEvent evt)//GEN-FIRST:event_zoomOutLabelMousePressed
    {//GEN-HEADEREND:event_zoomOutLabelMousePressed
        panel.onZoom(-1);
    }//GEN-LAST:event_zoomOutLabelMousePressed

    private void saveLabelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_saveLabelMouseClicked
    {//GEN-HEADEREND:event_saveLabelMouseClicked
        Path file = panel.getWorldFile();
        if(file == null)
        {
            if(fileChooser.showSaveDialog(panel) != JFileChooser.APPROVE_OPTION)
                return;
            
            file = fileChooser.getSelectedFile().toPath();
        }
        
        try(OutputStream out = Files.newOutputStream(file))
        {
            WorldStorage.serialize(panel.getWorld(), out);
        }catch(IOException e){
            e.printStackTrace();
        }
    }//GEN-LAST:event_saveLabelMouseClicked

    private void loadLabelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_loadLabelMouseClicked
    {//GEN-HEADEREND:event_loadLabelMouseClicked
        if(fileChooser.showOpenDialog(panel) == JFileChooser.APPROVE_OPTION)
        {
            Path p = fileChooser.getSelectedFile().toPath();
            try(InputStream in = Files.newInputStream(p))
            {
                World w = WorldStorage.deserialize(in);
                panel.setWorld(w, p);
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_loadLabelMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel keyBottomLabel;
    private javax.swing.JLabel keyLeftLabel;
    private javax.swing.JLabel keyRightLabel;
    private javax.swing.JLabel keyTopLabel;
    private javax.swing.JLabel loadLabel;
    private javax.swing.JLabel resetPositionLabel;
    private javax.swing.JLabel saveLabel;
    private javax.swing.JLabel zoomInLabel;
    private javax.swing.JLabel zoomOutLabel;
    // End of variables declaration//GEN-END:variables
}

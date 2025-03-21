package org.exolin.citysim;

import java.awt.Point;

/**
 *
 * @author Thomas
 */
public class GameData extends javax.swing.JPanel implements GamePanelListener
{
    private GamePanel panel;
    
    /**
     * Creates new form GameData
     */
    public GameData()
    {
        initComponents();
    }

    @Override
    public void created(GamePanel panel)
    {
        this.panel = panel;
    }

    @Override
    public void zoomChanged(int zoom, double zoomFactor)
    {
        String f;
        if(zoom == 0)
            f = "full view";
        else if(zoomFactor < 1)
            f = "zoom out "+(1/zoomFactor);
        else
            f = "zoom in "+zoomFactor;
        
        zoomValueLabel.setText(zoom+" | "+f);
    }

    @Override
    public void offsetChanged(int xoffset, int yoffset)
    {
        offsetValueLabel.setText(xoffset+"/"+yoffset);
    }

    @Override
    public void onSelectionChanged(Point p)
    {
        tilePosLabel.setText(p+"");
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

        jLabel1 = new javax.swing.JLabel();
        zoomValueLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        offsetValueLabel = new javax.swing.JLabel();
        resetPositionLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        tilePosLabel = new javax.swing.JLabel();

        jLabel1.setText("Zoom:");

        zoomValueLabel.setText("undefined");
        zoomValueLabel.setName(""); // NOI18N

        jLabel2.setText("Offset:");

        offsetValueLabel.setText("undefined");

        resetPositionLabel.setText("Reset position");
        resetPositionLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseClicked(java.awt.event.MouseEvent evt)
            {
                resetPositionLabelMouseClicked(evt);
            }
        });

        jLabel3.setText("Current tile:");

        tilePosLabel.setText("undefined");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(zoomValueLabel)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(offsetValueLabel)
                .addGap(40, 40, 40)
                .addComponent(resetPositionLabel)
                .addGap(36, 36, 36)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tilePosLabel)
                .addContainerGap(41, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(zoomValueLabel)
                    .addComponent(jLabel2)
                    .addComponent(offsetValueLabel)
                    .addComponent(resetPositionLabel)
                    .addComponent(jLabel3)
                    .addComponent(tilePosLabel))
                .addContainerGap(21, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void resetPositionLabelMouseClicked(java.awt.event.MouseEvent evt)//GEN-FIRST:event_resetPositionLabelMouseClicked
    {//GEN-HEADEREND:event_resetPositionLabelMouseClicked
        panel.resetPosition();
    }//GEN-LAST:event_resetPositionLabelMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel offsetValueLabel;
    private javax.swing.JLabel resetPositionLabel;
    private javax.swing.JLabel tilePosLabel;
    private javax.swing.JLabel zoomValueLabel;
    // End of variables declaration//GEN-END:variables
}

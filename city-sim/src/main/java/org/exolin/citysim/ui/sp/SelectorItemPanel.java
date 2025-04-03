package org.exolin.citysim.ui.sp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Objects;
import javax.swing.ImageIcon;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.ui.Action;
import org.exolin.citysim.ui.BuildingAction;

/**
 *
 * @author Thomas
 */
public class SelectorItemPanel extends javax.swing.JPanel
{
    private final Action action;
    
    public SelectorItemPanel(Action action)
    {
        this.action = Objects.requireNonNull(action);
        
        initComponents();
        
        if(action instanceof BuildingAction ba)
        {
            BuildingType building = ba.getBuilding();
            
            imageLabel.setIcon(new ImageIcon(building.getDefaultImage()));
            imageLabel.setText("");
            nameLabel.setText(building.getName());
            sizeLabel.setText(building.getSize()+"x"+building.getSize());
        }
        else
        {
            imageLabel.setIcon(null);//new ImageIcon(action.));
            imageLabel.setText("");
            nameLabel.setText(action.getName());
            sizeCaptionLabel.setVisible(false);
            sizeLabel.setVisible(false);
        }
        
        setPreferredSize(new Dimension(300, 100));
        
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                getSelectorPanel().select(SelectorItemPanel.this);
            }
        });
        setSelected(false);
    }

    public Action getAction()
    {
        return action;
    }
    
    private SelectorPanel getSelectorPanel()
    {
        return (SelectorPanel)getParent().getParent().getParent().getParent();
    }
    
    void setSelected(boolean selected)
    {
        Color bg = selected ? Color.GRAY.brighter().brighter() : Color.GRAY.brighter();
        setBackground(bg);
        jPanel1.setBackground(bg);
        jPanel2.setBackground(bg);
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

        jPanel1 = new javax.swing.JPanel();
        imageLabel = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        nameLabel = new javax.swing.JLabel();
        sizeCaptionLabel = new javax.swing.JLabel();
        sizeLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        jPanel1.setPreferredSize(new java.awt.Dimension(150, 300));
        jPanel1.setLayout(new java.awt.BorderLayout());

        imageLabel.setText("jLabel1");
        jPanel1.add(imageLabel, java.awt.BorderLayout.CENTER);

        add(jPanel1, java.awt.BorderLayout.LINE_START);

        nameLabel.setText("jLabel2");

        sizeCaptionLabel.setText("Size:");

        sizeLabel.setText("jLabel2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(sizeCaptionLabel)
                        .addGap(18, 18, 18)
                        .addComponent(sizeLabel))
                    .addComponent(nameLabel))
                .addContainerGap(158, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(nameLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sizeCaptionLabel)
                    .addComponent(sizeLabel))
                .addContainerGap(35, Short.MAX_VALUE))
        );

        add(jPanel2, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imageLabel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel sizeCaptionLabel;
    private javax.swing.JLabel sizeLabel;
    // End of variables declaration//GEN-END:variables
}

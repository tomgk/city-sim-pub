package org.exolin.citysim.ui.sp;

import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 *
 * @author Thomas
 */
public class PowerPlantSelectorPanel extends javax.swing.JPanel
{
    /**
     * Creates new form PowerPlantSelectorPanel
     * @param megaWatt
     * @param buildingCost
     * @param name
     */
    public PowerPlantSelectorPanel(int megaWatt, int buildingCost, String name, Image image, Runnable r)
    {
        initComponents();
        plantButton.setText("<html>"+megaWatt+" MW $"+buildingCost+"<br>"+name+"</html>");
        plantButton.setIcon(new ImageIcon(image));
        plantButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        plantButton.setHorizontalTextPosition(SwingConstants.CENTER);
        plantButton.addActionListener(e -> r.run());
        
        plantButton.setUI(new BasicButtonUI());
        
        //TODO
        infoButton.setEnabled(false);
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

        buttonPanel = new javax.swing.JPanel();
        infoButton = new javax.swing.JButton();
        plantButton = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout());

        infoButton.setText("Info");
        buttonPanel.add(infoButton);

        add(buttonPanel, java.awt.BorderLayout.PAGE_START);

        plantButton.setText("jButton1");
        add(plantButton, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JButton infoButton;
    private javax.swing.JButton plantButton;
    // End of variables declaration//GEN-END:variables
}

package org.exolin.citysim.ui.sp;

import java.awt.GridLayout;
import java.awt.Window;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.exolin.citysim.utils.ImageUtils;

/**
 *
 * @author Thomas
 */
public class PowerPlantSelectorDialog extends JDialog
{
    public PowerPlantSelectorDialog(Window parent/*, List<Building> plants*/)
    {
        super(parent, "Select Power Plant");
        setModal(true);
        setLayout(new GridLayout(1, 1));
        
        add(new PowerPlantSelectorPanel(50, 2000, "Gas Power", ImageUtils.loadImage("plants/gas_plant")));
        pack();
    }
    
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        PowerPlantSelectorDialog d = new PowerPlantSelectorDialog(f);
        d.setVisible(true);
    }
}

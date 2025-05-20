package org.exolin.citysim.ui.sp;

import java.awt.GridLayout;
import java.awt.Window;
import java.util.List;
import javax.swing.JDialog;
import javax.swing.JFrame;
import org.exolin.citysim.bt.buildings.Plants;
import org.exolin.citysim.model.building.BuildingType;

/**
 *
 * @author Thomas
 */
public class PowerPlantSelectorDialog extends JDialog
{
    public PowerPlantSelectorDialog(Window parent, List<BuildingType> plants)
    {
        super(parent, "Select Power Plant");
        setModal(true);
        setLayout(new GridLayout(2, 2));
        
        for(BuildingType bt: plants)
            add(new PowerPlantSelectorPanel(
                    bt.getCustom(Plants.MEGA_WATT, Integer.class),
                    bt.getCost(),
                    bt.getName(),
                    bt.getDefaultImage()));
        
        //add(new PowerPlantSelectorPanel(50, 2000, "Gas Power", ImageUtils.loadImage("plants/gas_plant")));
        pack();
    }
    
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        PowerPlantSelectorDialog d = new PowerPlantSelectorDialog(f, List.of(Plants.gas_plant));
        d.setVisible(true);
    }
}

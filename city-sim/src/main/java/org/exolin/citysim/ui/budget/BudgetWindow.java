package org.exolin.citysim.ui.budget;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.border.MatteBorder;
import org.exolin.citysim.bt.Streets;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.Building;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public class BudgetWindow extends JDialog
{
    private final Map<BudgetCategory, BudgetLinePanel> categories = new LinkedHashMap<>();
    private final BudgetLinePanel sum = new BudgetLinePanel("", Optional.empty());
    
    private static final List<BudgetCategory> list = List.of(
            new ZoneCategory(Zones.zone_residential),
            new ZoneCategory(Zones.zone_business),
            new ZoneCategory(Zones.zone_industrial),
            new StreetCategory(Streets.street),
            new StreetCategory(Streets.rail),
            new ZoneCategory(Zones.zone_plants)
    );
    
    public BudgetWindow(JFrame frame)
    {
        super(frame, true);
        setLayout(new GridLayout(0, 1));
        
        for(BudgetCategory category : list)
        {
            BudgetLinePanel p = new BudgetLinePanel(category.getTitle(), Optional.of(category.isIncome()));
            categories.put(category, p);
            add(p);
        }
        
        sum.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        add(sum);
        
        setTitle("Budget");
        pack();
        setLocationRelativeTo(frame);
        //setAlwaysOnTop(true);
    }
    
    public void update(World w)
    {
        for(BudgetLinePanel l : categories.values())
            l.resetValues();
        sum.resetValues();
        
        for(Building b : w.getBuildings())
        {
            BudgetCategory category = BudgetCategory.getFor(b.getType());
            
            if(category == null)
                continue;
            
            BudgetLinePanel panel = categories.get(category);
            if(panel == null)
            {
                new IllegalArgumentException("not mapped "+category).printStackTrace();
                continue;
            }

            panel.updateValues(b.getTaxRevenue(), b.getMaintenance());
            sum.updateValues(b.getTaxRevenue(), b.getMaintenance());
        }
        
        for(BudgetLinePanel l : categories.values())
            l.showValues();
        sum.showValues();
    }
}

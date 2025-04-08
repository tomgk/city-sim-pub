package org.exolin.citysim.ui.budget;

import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.border.MatteBorder;
import org.exolin.citysim.model.Building;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public class BudgetWindow extends JFrame
{
    private final Map<BudgetCategory, BudgetLinePanel> categories = new LinkedHashMap<>();
    private final BudgetLinePanel sum = new BudgetLinePanel("");
    
    public BudgetWindow()
    {
        setLayout(new GridLayout(0, 1));
        
        for(BuildingType t : BuildingType.types())
        {
            BudgetCategory category = BudgetCategory.getFor(t);
            
            if(category == null)
                continue;
            
            //only add once
            if(categories.containsKey(category))
                continue;
            
            BudgetLinePanel p = new BudgetLinePanel(category.getTitle());
            categories.put(category, p);
            add(p);
        }
        
        sum.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        add(sum);
    }
    
    public void update(World w)
    {
        for(BudgetLinePanel l : categories.values())
            l.resetValues();
        
        for(Building b : w.getBuildings())
        {
            BudgetCategory category = BudgetCategory.getFor(b.getType());
            
            if(category == null)
                continue;
            
            categories.get(category).updateValues(b.getTaxRevenue(), b.getMaintenance());
        }
        
        for(BudgetLinePanel l : categories.values())
            l.showValues();
    }
}

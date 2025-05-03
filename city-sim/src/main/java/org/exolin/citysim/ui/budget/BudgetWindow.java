package org.exolin.citysim.ui.budget;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.MatteBorder;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.connections.SelfConnections;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public class BudgetWindow extends JDialog
{
    private final Map<BudgetCategory, BudgetLinePanel> categories = new LinkedHashMap<>();
    private final BudgetLinePanel sum = new BudgetLinePanel("", Optional.empty());
    
    private static final List<BudgetCategory> list = List.of(new ZoneCategory(Zones.residential),
            new ZoneCategory(Zones.business),
            new ZoneCategory(Zones.industrial),
            new SelfConnectionCategory(SelfConnections.street),
            new SelfConnectionCategory(SelfConnections.rail),
            new ZoneCategory(Zones.plants)
    );
    
    private final JPanel lines = new JPanel(new GridLayout(0, 1));
    private final DiffPanel diffPanel = new DiffPanel();
    
    public BudgetWindow(JFrame frame)
    {
        super(frame, true);
        setLayout(new BorderLayout());
        
        for(BudgetCategory category : list)
        {
            BudgetLinePanel p = new BudgetLinePanel(category.getTitle(), Optional.of(category.isIncome()));
            categories.put(category, p);
            lines.add(p);
        }
        
        sum.setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
        lines.add(sum);
        
        add(lines, BorderLayout.CENTER);
        add(diffPanel, BorderLayout.SOUTH);
        
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
        
        for(Structure b : w.getStructures())
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
        
        diffPanel.update(w, sum.getTaxRevenue(), sum.getExpenses());
    }
}

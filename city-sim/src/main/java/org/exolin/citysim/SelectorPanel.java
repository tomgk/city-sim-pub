package org.exolin.citysim;

import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Thomas
 */
public class SelectorPanel extends JPanel
{
    private final GamePanel panel;
    private SelectorItemPanel selected = null;
    
    public SelectorPanel(GamePanel panel)
    {
        this.panel = panel;
        setLayout(new GridLayout(0, 1));
    }
    
    public void add(Action action)
    {
        add(new SelectorItemPanel(action));
    }

    void select(SelectorItemPanel item)
    {
        if(selected != null)
            selected.setSelected(false);
        
        selected = item;
        if(selected != null)
        {
            panel.setAction(selected.getAction());
            selected.setSelected(true);
        }
        else
            panel.setAction(null);
    }
}

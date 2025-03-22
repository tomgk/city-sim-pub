package org.exolin.citysim.ui.sp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import org.exolin.citysim.ui.Action;
import org.exolin.citysim.ui.GamePanel;

/**
 *
 * @author Thomas
 */
public class SelectorPanel extends JPanel
{
    private final GamePanel panel;
    
    private final DefaultComboBoxModel<String> categoriesModel;
    private final JComboBox<String> categoriesCombobox;
    private final JPanel itemsPanel;
    private final Map<String, List<Action>> actions = new LinkedHashMap<>();
    private SelectorItemPanel selected = null;
    
    public SelectorPanel(GamePanel panel)
    {
        this.panel = panel;
        setLayout(new BorderLayout());
        
        this.categoriesModel = new DefaultComboBoxModel<>();
        this.categoriesCombobox = new JComboBox<>(categoriesModel);
        categoriesCombobox.addActionListener((ActionEvent e) ->
        {
            List<Action> a = actions.get((String)categoriesCombobox.getSelectedItem());
            setList(a != null ? a : List.of());
        });
        
        this.itemsPanel = new JPanel(new GridLayout(0, 1));
        
        add(categoriesCombobox, BorderLayout.NORTH);
        add(itemsPanel, BorderLayout.CENTER);
    }
    
    private void setList(List<Action> list)
    {
        itemsPanel.removeAll();
        for(Action action : list)
            itemsPanel.add(new SelectorItemPanel(action));
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
    
    public void add(String name, Action action)
    {
        if(!actions.containsKey(name))
        {
            actions.put(name, new ArrayList<>());
            categoriesModel.addElement(name);
        }
        
        actions.computeIfAbsent(name, n -> new ArrayList<>()).add(action);
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

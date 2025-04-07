package org.exolin.citysim.ui.sp;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import org.exolin.citysim.ui.Action;
import org.exolin.citysim.ui.GamePanel;

/**
 *
 * @author Thomas
 */
public class SelectorPanel extends JPanel
{
    private GamePanel panel;
    
    private final DefaultComboBoxModel<String> categoriesModel;
    private final JComboBox<String> categoriesCombobox;
    private final JPanel itemsPanel;
    private final Map<String, List<Action>> actions = new LinkedHashMap<>();
    private final Map<Action, String> actionCategories = new IdentityHashMap<>();
    private SelectorItemPanel selected = null;
    private Action setAction;
    
    public SelectorPanel()
    {
        setLayout(new BorderLayout());
        
        this.categoriesModel = new DefaultComboBoxModel<>();
        this.categoriesCombobox = new JComboBox<>(categoriesModel);
        categoriesCombobox.addActionListener((ActionEvent e) ->
        {
            executeSelect((String)categoriesCombobox.getSelectedItem());
        });
        
        this.itemsPanel = new JPanel(new GridLayout(0, 1));
        
        add(categoriesCombobox, BorderLayout.NORTH);
        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
    }
    
    private void executeSelect(String category)
    {
        List<Action> a = actions.get(category);
        setList(a != null ? a : List.of());
        //reset, so that current action isn't out another category
        SelectorPanel.this.panel.setAction(Action.NONE);
        setAction = null;
    }
    
    public void doneAdding()
    {
        executeSelect((String)categoriesCombobox.getSelectedItem());
    }

    public void setPanel(GamePanel panel)
    {
        this.panel = panel;
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
        
        actionCategories.put(action, name);
        actions.computeIfAbsent(name, n -> new ArrayList<>()).add(action);
    }

    void select(SelectorItemPanel item)
    {
        if(selected != null)
            selected.setSelected(false);
        
        selected = item;
        if(selected != null)
        {
            setAction = selected.getAction();
            panel.setAction(selected.getAction());
            selected.setSelected(true);
        }
        else
        {
            setAction = null;
            panel.setAction(null);
        }
    }

    public void setAction(Action newAction)
    {
        if(true)
            return;
        
        Objects.requireNonNull(newAction);
        
        //when change came from here then no change is needed
        if(setAction == newAction)
            return;
        
        String category = actionCategories.get(newAction);
        if(category == null)
            throw new IllegalArgumentException(newAction.getName()+" vs "+actionCategories);
        
        List<Action> cactions = this.actions.get(category);
        if(cactions == null)
            throw new IllegalArgumentException(newAction.getName());
        
        int index = cactions.indexOf(newAction);
        if(index == -1)
            throw new IllegalArgumentException();
        
        categoriesCombobox.setSelectedItem(category);
        setList(cactions);
        SelectorItemPanel component = (SelectorItemPanel)itemsPanel.getComponent(index);
        component.setSelected(true);
    }
}

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
import org.exolin.citysim.ui.GamePanel;
import org.exolin.citysim.ui.actions.Action;

/**
 *
 * @author Thomas
 */
public class SelectorPanel extends JPanel
{
    private GamePanel panel;
    
    private final DefaultComboBoxModel<Category> categoriesModel;
    private final JComboBox<Category> categoriesCombobox;
    private final JPanel itemsPanel;
    private final Map<String, Category> actions = new LinkedHashMap<>();
    private final Map<Action, Category> actionCategories = new IdentityHashMap<>();
    private SelectorItemPanel selected = null;
    private Action setAction;
    
    private static class Category
    {
        private final String title;
        private final List<Action> actions = new ArrayList<>();

        public Category(String title)
        {
            this.title = title;
        }

        @Override
        public String toString()
        {
            return title;
        }
    }
    
    public SelectorPanel()
    {
        setLayout(new BorderLayout());
        
        this.categoriesModel = new DefaultComboBoxModel<>();
        this.categoriesCombobox = new JComboBox<>(categoriesModel);
        categoriesCombobox.addActionListener((ActionEvent e) ->
        {
            executeSelect((Category)categoriesCombobox.getSelectedItem());
        });
        
        this.itemsPanel = new JPanel(new GridLayout(0, 1));
        
        add(categoriesCombobox, BorderLayout.NORTH);
        add(new JScrollPane(itemsPanel), BorderLayout.CENTER);
    }
    
    private void executeSelect(Category category)
    {
        setList(category);
        //reset, so that current action isn't out another category
        SelectorPanel.this.panel.setAction(Action.NONE);
        setAction = null;
    }
    
    public void doneAdding()
    {
        executeSelect((Category)categoriesCombobox.getSelectedItem());
    }

    public void setPanel(GamePanel panel)
    {
        this.panel = panel;
    }
    
    private void setList(Category list)
    {
        itemsPanel.removeAll();
        for(Action action : list.actions)
            itemsPanel.add(new SelectorItemPanel(action));
        itemsPanel.revalidate();
        itemsPanel.repaint();
    }
    
    public void add(String name, Action action)
    {
        Category c = actions.get(name);
        if(c == null)
        {
            c = new Category(name);
            actions.put(name, c);
            categoriesModel.addElement(c);
        }
        
        actionCategories.put(action, c);
        c.actions.add(action);
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
        
        Category category = actionCategories.get(newAction);
        if(category == null)
            throw new IllegalArgumentException(newAction.getName()+" vs "+actionCategories);
        
        int index = category.actions.indexOf(newAction);
        if(index == -1)
            throw new IllegalArgumentException();
        
        categoriesCombobox.setSelectedItem(category);
        setList(category);
        SelectorItemPanel component = (SelectorItemPanel)itemsPanel.getComponent(index);
        component.setSelected(true);
    }
}

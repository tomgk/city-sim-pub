package org.exolin.citysim.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public class LoadGame extends JFrame
{
    private final List<World> worlds;
    private final JList list;
    private final Consumer<World> choice;
    
    public LoadGame(List<World> worlds, Consumer<World> choice)
    {
        this.worlds = Objects.requireNonNull(worlds);
        this.choice = Objects.requireNonNull(choice);
        setTitle("Select City");
        setLayout(new BorderLayout());
        
        list = new JList(worlds.stream().map(World::getName).toArray());
        add(list, BorderLayout.CENTER);
        list.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                if(e.getClickCount() == 2)
                    executeSelection();
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 0));
        
        JButton choose = new JButton("OK");
        buttonPanel.add(choose);
        choose.addActionListener(a -> executeSelection());
        
        JButton close = new JButton("Exit");
        buttonPanel.add(close);
        close.addActionListener(a->setVisible(false));
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private void executeSelection()
    {
        int index = list.getSelectedIndex();
        if(index == -1)
            return;

        World w = worlds.get(index);

        setVisible(false);
        choice.accept(w);
    }
}

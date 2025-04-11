package org.exolin.citysim.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.exolin.citysim.model.World;
import static org.exolin.citysim.model.Worlds.DEFAULT_GRID_SIZE;
import static org.exolin.citysim.model.Worlds.DEFAULT_MONEY;

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
        
        list = new JList(Stream.concat(Stream.of("New World"), worlds.stream().map(World::getName)).toArray());
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
        
        World w;
        
        if(index == 0)
        {
            String name = JOptionPane.showInputDialog(this, "City Name", "New World", JOptionPane.PLAIN_MESSAGE);
            w = new World(name, DEFAULT_GRID_SIZE, DEFAULT_MONEY);
        }
        else
            w = worlds.get(index);

        setVisible(false);
        choice.accept(w);
    }
}

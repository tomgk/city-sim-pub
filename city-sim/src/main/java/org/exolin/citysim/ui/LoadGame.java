package org.exolin.citysim.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.exolin.citysim.model.SimulationSpeed;
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
    private final Consumer<Optional<World>> choice;
    
    public LoadGame(List<World> worlds, Consumer<Optional<World>> choice)
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
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e)
            {
                chose(null);
            }
        });
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1, 0));
        
        JButton choose = new JButton("OK");
        buttonPanel.add(choose);
        choose.addActionListener(a -> executeSelection());
        
        JButton close = new JButton("Exit");
        buttonPanel.add(close);
        close.addActionListener(a -> chose(null));
        
        add(buttonPanel, BorderLayout.SOUTH);
        
        pack();
        setLocationRelativeTo(null);
    }
    
    private boolean chosen = false;
    
    private void executeSelection()
    {
        int index = list.getSelectedIndex();
        if(index == -1)
            return;
        
        World w;
        
        if(index == 0)
        {
            String name = JOptionPane.showInputDialog(this, "City Name", "New World", JOptionPane.PLAIN_MESSAGE);
            if(name == null)
                return;
            
            w = new World(name, DEFAULT_GRID_SIZE, DEFAULT_MONEY, SimulationSpeed.SPEED1);
        }
        else
            w = worlds.get(index-1);

        chose(w);
    }
    
    private void chose(World w)
    {
        if(chosen)
            return;
        
        chosen = true;
        setVisible(false);
        choice.accept(Optional.of(w));
    }
}

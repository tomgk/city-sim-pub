package org.exolin.citysim.ui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JList;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public class LoadGame extends JFrame
{
    public LoadGame(List<World> worlds)
    {
        setTitle("Select City");
        setLayout(new BorderLayout());
        add(new JList(worlds.stream().map(World::getName).toArray()));
        pack();
        setLocationRelativeTo(null);
    }
}

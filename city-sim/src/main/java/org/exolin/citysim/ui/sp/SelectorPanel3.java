package org.exolin.citysim.ui.sp;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.Objects;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import org.exolin.citysim.model.Worlds;
import org.exolin.citysim.ui.GamePanel;
import org.exolin.citysim.ui.GamePanelListener;
import org.exolin.citysim.ui.actions.Action;

/**
 *
 * @author Thomas
 */
public class SelectorPanel3 extends JPanel
{
    private final RCIPanel rciPanel;
    private final GamePanel world;
    
    public SelectorPanel3(GamePanel world)
    {
        this.world = Objects.requireNonNull(world);
        
        setLayout(new GridBagLayout());
        
        registerButton(0, "bulldoze.png", Action.NONE);
        registerButton(2, "tree_water.png", Action.NONE);
        registerButton(4, "emergency.png", Action.NONE);
        ++y;
        
        registerButton(0, "electricity.png", Action.NONE);
        registerButton(2, "water.png", Action.NONE);
        registerButton(4, "city_hall.png", Action.NONE);
        ++y;
        
        registerButton(0, "street.png", Action.NONE);
        registerButton(2, "rail.png", Action.NONE);
        registerButton(4, "port.png", Action.NONE);
        ++y;
        
        registerButton(0, "residential.png", Action.NONE);
        registerButton(2, "business.png", Action.NONE);
        registerButton(4, "industry.png", Action.NONE);
        ++y;
        
        registerButton(0, "school.png", Action.NONE);
        registerButton(2, "police.png", Action.NONE);
        registerButton(4, "party.png", Action.NONE);
        ++y;
        
        w = 3;
        registerButton(0, "sign.png", Action.NONE);
        registerButton(3, "info.png", Action.NONE);
        ++y;

        w = 3;
        registerButton(0, "turn_left.png", Action.NONE);
        registerButton(3, "turn_right.png", Action.NONE);
        ++y;

        w = 2;
        registerButton(0, "zoom_out.png", Action.NONE);
        registerButton(2, "zoom_in.png", Action.NONE);
        registerButton(4, "center.png", Action.NONE);
        ++y;
        
        int yRCI = y;
        
        w = 2;
        registerButton(0, "map.png", Action.NONE);
        registerButton(2, "diagrams.png", Action.NONE);
        ++y;
        
        w = 2;
        registerButton(0, "population.png", Action.NONE);
        registerButton(2, "industries.png", Action.NONE);
        ++y;
        
        w = 2;
        registerButton(0, "neighbors.png", Action.NONE);
        registerButton(2, "budget.png", Action.NONE);
        ++y;
        
        {
            rciPanel = new RCIPanel(50, -30, 70);
            rciPanel.setPreferredSize(new Dimension(30, 90));

            GridBagConstraints constraints = createConstraints(4, yRCI, 2, 3);

            add(rciPanel, constraints);
        }
    }
    
    private int y = 0;
    private int w = 2;
    
    private GridBagConstraints createConstraints(int x, int y, int w, int h)
    {
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = h;
        constraints.fill = GridBagConstraints.BOTH;
        return constraints;
    }

    private void registerButton(int x, String path, Action a)
    {
        AbstractButton button = Action.isNone(a) ? new JButton() : new JToggleButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/org/exolin/citysim/menu/"+path));
        button.setIcon(icon);
        button.setPreferredSize(new Dimension(w * 10, 30));
        
        button.addActionListener(e -> world.setAction(a));
        
        GridBagConstraints constraints = createConstraints(x, y, w, 1);
        
        add(button, constraints);
    }
    
    public static void main(String[] args)
    {
        GamePanel w = new GamePanel(Worlds.World1(), new JFrame(), new GamePanelListener()
        {
            @Override
            public void created(GamePanel panel)
            {
            }

            @Override
            public void onActionChanged(Action newAction)
            {
            }
        });
        
        JFrame f = new JFrame();
        f.add(new SelectorPanel3(w), BorderLayout.CENTER);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

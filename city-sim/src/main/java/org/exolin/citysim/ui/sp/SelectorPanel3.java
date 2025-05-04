package org.exolin.citysim.ui.sp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import org.exolin.citysim.bt.Zones;
import static org.exolin.citysim.bt.connections.SelfConnections.rail;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import static org.exolin.citysim.bt.connections.SelfConnections.water;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.Worlds;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.ui.ErrorDisplay;
import org.exolin.citysim.ui.GamePanel;
import org.exolin.citysim.ui.GamePanelListener;
import org.exolin.citysim.ui.actions.Action;
import org.exolin.citysim.ui.actions.PlaceTrees;
import org.exolin.citysim.ui.actions.StreetBuilder;
import org.exolin.citysim.ui.actions.ZonePlacement;

/**
 *
 * @author Thomas
 */
public class SelectorPanel3 extends JPanel
{
    private final RCIPanel rciPanel;
    private GamePanel gamePanel;
    private final GetWorld getWorld = () -> gamePanel.getWorld();
    
    public SelectorPanel3()
    {
        setLayout(new GridBagLayout());
        
        registerButton(0, "bulldoze.png", Action.NONE);
        registerButton(2, "tree_water.png", Map.of(
                "Trees", new PlaceTrees(getWorld)
        ));
        registerButton(4, "emergency.png", Action.NONE);
        ++y;
        
        registerButton(0, "electricity.png", Action.NONE);
        registerButton(2, "water.png", new StreetBuilder(getWorld, water, false));
        registerButton(4, "city_hall.png", Action.NONE);
        ++y;
        
        registerButton(0, "street.png", new StreetBuilder(getWorld, street, true));
        registerButton(2, "rail.png", new StreetBuilder(getWorld, rail, true));
        registerButton(4, "port.png", Action.NONE);
        ++y;
        
        registerButton(0, "residential.png", new ZonePlacement(getWorld, Zones.residential, ZoneType.Variant.LOW_DENSITY));
        registerButton(2, "business.png", new ZonePlacement(getWorld, Zones.business, ZoneType.Variant.LOW_DENSITY));
        registerButton(4, "industry.png", new ZonePlacement(getWorld, Zones.industrial, ZoneType.Variant.LOW_DENSITY));
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
        registerButton(0, "turn_left.png", e -> gamePanel.setView(gamePanel.getView().getPrev()));
        registerButton(3, "turn_right.png", e -> gamePanel.setView(gamePanel.getView().getNext()));
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

    public void setAction(Action newAction)
    {
        
    }

    public void setGamePanel(GamePanel gamePanel)
    {
        this.gamePanel = gamePanel;
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
        registerButton(x, path, e -> gamePanel.setAction(a));
    }
    
    private void registerButton(int x, String path, Map<String, Action> a)
    {
        JPopupMenu m = new JPopupMenu();
        for(Map.Entry<String, Action> e : a.entrySet())
        {
            JMenuItem i = new JMenuItem(e.getKey());
            i.addActionListener(evt -> gamePanel.setAction(e.getValue()));
            System.out.println(e);
        }
        
        registerButton(x, path, e -> {
            try{
                m.show((Component)e.getSource(), e.getX(), e.getY());
            }catch(ClassCastException ex){
                ErrorDisplay.show(this, ex);
            }
        });
    }
    
    public static interface ButtonListener
    {
        void perform(MouseEvent e);
    }
    
    private void registerButton(int x, String path, ButtonListener a)
    {
        AbstractButton button = /*Action.isNone(a) ?*/ new JButton() /*: new JToggleButton()*/;
        ImageIcon icon = new ImageIcon(getClass().getResource("/org/exolin/citysim/menu/"+path));
        button.setIcon(icon);
        button.setPreferredSize(new Dimension(w * 10, 30));
        
        button.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                a.perform(e);
            }
        });
        
        GridBagConstraints constraints = createConstraints(x, y, w, 1);
        
        add(button, constraints);
    }
    
    public static void main(String[] args)
    {
        GamePanel gp = new GamePanel(Worlds.World1(), new JFrame(), new GamePanelListener()
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
        SelectorPanel3 sp = new SelectorPanel3();
        sp.setGamePanel(gp);
        f.add(sp, BorderLayout.CENTER);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

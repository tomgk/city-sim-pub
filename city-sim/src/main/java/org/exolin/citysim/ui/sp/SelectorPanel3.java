package org.exolin.citysim.ui.sp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingUtilities;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.buildings.Plants;
import static org.exolin.citysim.bt.connections.SelfConnections.rail;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import static org.exolin.citysim.bt.connections.SelfConnections.water;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.ui.ErrorDisplay;
import org.exolin.citysim.ui.GamePanel;
import org.exolin.citysim.ui.actions.Action;
import org.exolin.citysim.ui.actions.PlaceBuilding;
import org.exolin.citysim.ui.actions.PlaceTrees;
import org.exolin.citysim.ui.actions.StreetBuilder;
import org.exolin.citysim.ui.actions.TearDownAction;
import org.exolin.citysim.ui.actions.ZonePlacement;
import org.exolin.citysim.ui.budget.BudgetWindow;

/**
 *
 * @author Thomas
 */
public class SelectorPanel3 extends JPanel
{
    private final RCIPanel rciPanel;
    private GamePanel gamePanel;
    private final GetWorld world = () -> gamePanel.getWorld();
    
    private static final String LIGHT = "Light";
    private static final String HIGH = "High";
    
    public SelectorPanel3(BudgetWindow budgetWindow)
    {
        setLayout(new GridBagLayout());
        
        registerButton(0, "bulldoze.png", TearDownAction.createTearDown(world));
        registerButton(2, "tree_water.png", Map.of(
                "Trees", new PlaceTrees(world, false),
                "Grass", new PlaceTrees(world, true),
                "Water", new StreetBuilder(world, water, false)
        ));
        registerButton(4, "emergency.png", Action.NONE);
        ++y;
        
        registerButton(0, "electricity.png", Map.of(
                "Gas", new PlaceBuilding(world, Plants.gas_plant),
                "Oil", new PlaceBuilding(world, Plants.oil_plant),
                "Solar", new PlaceBuilding(world, Plants.plant_solar),
                "Protest", new PlaceBuilding(world, Plants.protest),
                "Pump", new PlaceBuilding(world, Plants.pump)
        ));
        registerButton(2, "water.png", Action.NONE);
        registerButton(4, "city_hall.png", Action.NONE);
        ++y;
        
        registerButton(0, "street.png", new StreetBuilder(world, street, true));
        registerButton(2, "rail.png", new StreetBuilder(world, rail, true));
        registerButton(4, "port.png", Action.NONE);
        ++y;
        
        registerButton(0, "residential.png", Map.of(
                LIGHT, new ZonePlacement(world, Zones.low_residential, ZoneType.Variant.DEFAULT),
                HIGH, new ZonePlacement(world, Zones.residential, ZoneType.Variant.DEFAULT)
        ));
        registerButton(2, "business.png", Map.of(
                LIGHT, new ZonePlacement(world, Zones.low_business, ZoneType.Variant.DEFAULT),
                HIGH, new ZonePlacement(world, Zones.business, ZoneType.Variant.DEFAULT)
        ));
        registerButton(4, "industry.png", Map.of(
                LIGHT, new ZonePlacement(world, Zones.low_industrial, ZoneType.Variant.DEFAULT),
                HIGH, new ZonePlacement(world, Zones.industrial, ZoneType.Variant.DEFAULT)
        ));
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
        registerButton(0, "turn_left.png", e -> gamePanel.rotateLeft(), false);
        registerButton(3, "turn_right.png", e -> gamePanel.rotateRight(), false);
        ++y;

        w = 2;
        registerButton(0, "zoom_out.png", e -> gamePanel.onZoom(-1), false);
        registerButton(2, "zoom_in.png", e -> gamePanel.onZoom(+1), false);
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
        registerButton(2, "budget.png", e -> {
            budgetWindow.update(world.get());
            budgetWindow.setVisible(true);
        }, false);
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
        registerButton(x, path, e -> gamePanel.setAction(a), Action.isNone(a));
    }
    
    private void registerButton(int x, String path, Map<String, Action> a)
    {
        JDialog f = new JDialog(SwingUtilities.getWindowAncestor(this));
        //f.setModal(true);
        f.setUndecorated(true);
        JList<String> list = new JList<>(a.keySet().toArray(String[]::new));
        list.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                //if(e.getClickCount() == 2)
                {
                    f.setVisible(false);
            
                    String sel = list.getSelectedValue();
                    System.out.println("SelectedValue="+sel);
                    Action action = a.get(sel);
                    gamePanel.setAction(action);
                }
            }
        });
        f.addWindowFocusListener(new WindowAdapter(){
            @Override
            public void windowLostFocus(WindowEvent e)
            {
                f.setVisible(false);
            }
        });
        f.add(list, BorderLayout.CENTER);
        f.pack();
        f.setLocationRelativeTo(null);
        registerButton(x, path, e -> {
            f.setLocation(e.getXOnScreen(), e.getYOnScreen());
            f.setAlwaysOnTop(false);
            f.setAlwaysOnTop(f.isAlwaysOnTop());
            f.setVisible(true);
        }, false);
        
        if(true)
            return;
        
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
        }, false);
    }
    
    public static interface ButtonListener
    {
        void perform(MouseEvent e);
    }
    
    private void registerButton(int x, String path, ButtonListener a, boolean disabled)
    {
        AbstractButton button = /*Action.isNone(a) ?*/ new JButton() /*: new JToggleButton()*/;
        ImageIcon icon = new ImageIcon(getClass().getResource("/org/exolin/citysim/menu/"+path));
        button.setIcon(icon);
        button.setPreferredSize(new Dimension(w * 10, 30));
        button.setEnabled(!disabled);
        
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
}

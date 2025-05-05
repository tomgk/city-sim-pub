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
import java.awt.event.WindowFocusListener;
import java.util.Map;
import javax.swing.AbstractButton;
import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.bt.buildings.Plants;
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
import org.exolin.citysim.ui.actions.PlaceBuilding;
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
    
    private static final String LIGHT = "Light";
    private static final String HIGH = "High";
    
    public SelectorPanel3()
    {
        setLayout(new GridBagLayout());
        
        registerButton(0, "bulldoze.png", Action.NONE);
        registerButton(2, "tree_water.png", Map.of(
                "Trees", new PlaceTrees(getWorld),
                "Water", new StreetBuilder(getWorld, water, false)
        ));
        registerButton(4, "emergency.png", Action.NONE);
        ++y;
        
        registerButton(0, "electricity.png", Map.of(
                "Gas", new PlaceBuilding(getWorld, Plants.gas_plant),
                "Oil", new PlaceBuilding(getWorld, Plants.oil_plant),
                "Solar", new PlaceBuilding(getWorld, Plants.plant_solar),
                "Protest", new PlaceBuilding(getWorld, Plants.protest),
                "Pump", new PlaceBuilding(getWorld, Plants.pump)
        ));
        registerButton(2, "water.png", new StreetBuilder(getWorld, water, false));
        registerButton(4, "city_hall.png", Action.NONE);
        ++y;
        
        registerButton(0, "street.png", new StreetBuilder(getWorld, street, true));
        registerButton(2, "rail.png", new StreetBuilder(getWorld, rail, true));
        registerButton(4, "port.png", Action.NONE);
        ++y;
        
        registerButton(0, "residential.png", Map.of(
                LIGHT, new ZonePlacement(getWorld, Zones.residential, ZoneType.Variant.LOW_DENSITY),
                HIGH, new ZonePlacement(getWorld, Zones.residential, ZoneType.Variant.DEFAULT)
        ));
        registerButton(2, "business.png", Map.of(
                LIGHT, new ZonePlacement(getWorld, Zones.business, ZoneType.Variant.LOW_DENSITY),
                HIGH, new ZonePlacement(getWorld, Zones.business, ZoneType.Variant.DEFAULT)
        ));
        registerButton(4, "industry.png", Map.of(
                LIGHT, new ZonePlacement(getWorld, Zones.industrial, ZoneType.Variant.LOW_DENSITY),
                HIGH, new ZonePlacement(getWorld, Zones.industrial, ZoneType.Variant.DEFAULT)
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
        registerButton(0, "turn_left.png", e -> gamePanel.setView(gamePanel.getView().getPrev()), true);
        registerButton(3, "turn_right.png", e -> gamePanel.setView(gamePanel.getView().getNext()), true);
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

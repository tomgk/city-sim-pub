package org.exolin.citysim.ui.sp;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Window;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
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
import static org.exolin.citysim.bt.connections.SelfConnections.circuit;
import static org.exolin.citysim.bt.connections.SelfConnections.rail;
import static org.exolin.citysim.bt.connections.SelfConnections.street;
import static org.exolin.citysim.bt.connections.SelfConnections.water;
import org.exolin.citysim.model.GetWorld;
import org.exolin.citysim.model.RCI;
import org.exolin.citysim.model.zone.ZoneType;
import org.exolin.citysim.ui.ErrorDisplay;
import org.exolin.citysim.ui.GamePanel;
import org.exolin.citysim.ui.GamePanelListener;
import org.exolin.citysim.ui.WorldView;
import org.exolin.citysim.ui.actions.Action;
import org.exolin.citysim.ui.actions.PlaceTrees;
import org.exolin.citysim.ui.actions.StreetBuilder;
import org.exolin.citysim.ui.actions.TearDownAction;
import org.exolin.citysim.ui.actions.ZonePlacement;
import org.exolin.citysim.ui.budget.BudgetWindow;
import org.exolin.citysim.ui.sp.powerplant.PowerPlantSelectorDialog;

/**
 *
 * @author Thomas
 */
public class SelectorPanel3 extends JPanel implements GamePanelListener
{
    private final RCIPanel rciPanel;
    private GamePanel gamePanel;
    private final GetWorld world = GetWorld.delegate(() -> gamePanel.getWorldHolder());
    
    private static final String LIGHT = "Light";
    private static final String HIGH = "High";
    
    
    /**
     * 
     * @param budgetWindow optional because of unit tests
     */
    public SelectorPanel3(GamePanel gamePanel, Optional<BudgetWindow> budgetWindow)
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
        
        registerButtonBL(0, "electricity.png", Map.of(
                //. to keep key before Plant
                ".Power Line", new SelectAction(new StreetBuilder(world, circuit, true)),
                "Plant", e -> {
                    Window window = SwingUtilities.getWindowAncestor(this);
                    PowerPlantSelectorDialog ppsd = new PowerPlantSelectorDialog(window, gamePanel, Plants.ALL);
                    ppsd.setLocationRelativeTo(window);
                    ppsd.setVisible(true);
                }
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
        
        {
            Map<String, ButtonListener> maps = new LinkedHashMap<>();
            for(WorldView v: WorldView.values())
                maps.put(v.getTitle(), e -> gamePanel.setView(v));

            registerButtonBL(0, "map.png", maps);
        }
        registerButton(2, "diagrams.png", Action.NONE);
        ++y;
        
        w = 2;
        registerButton(0, "population.png", Action.NONE);
        registerButton(2, "industries.png", Action.NONE);
        ++y;
        
        w = 2;
        registerButton(0, "neighbors.png", Action.NONE);
        
        ButtonListener bl;
        if(budgetWindow.isPresent())
        {
            BudgetWindow b = budgetWindow.get();
            bl = e -> {
                b.update(world.get());
                b.setVisible(true);
            };
        }
        else
            bl = e -> {};
        
        registerButton(2, "budget.png", bl, budgetWindow.isEmpty());
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
        Map<String, ButtonListener> bla = a.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey,
                                  e -> new SelectAction(e.getValue())));
        
        registerButtonBL(x, path, bla);
    }
    
    private void registerButtonBL(int x, String path, Map<String, ButtonListener> a)
    {
        //for unit tests
        if(GraphicsEnvironment.isHeadless())
            return;
        
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
                    ButtonListener action = a.get(sel);
                    action.perform(e);
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
        for(Map.Entry<String, ButtonListener> e : a.entrySet())
        {
            JMenuItem i = new JMenuItem(e.getKey());
            i.addActionListener(evt -> e.getValue().perform(null));
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

    public void setRCI(RCI rci)
    {
        rciPanel.set(rci);
    }

    @Override
    public void created(GamePanel panel)
    {
    }

    @Override
    public void onActionChanged(Action newAction)
    {
        setAction(newAction);
    }

    @Override
    public void onRCIChanged(RCI rci)
    {
        setRCI(rci);
    }
    
    public static interface ButtonListener
    {
        static ButtonListener NOTHING = e -> {};
        void perform(MouseEvent e);
    }
    
    public class SelectAction implements ButtonListener
    {
        private final Action action;

        public SelectAction(Action action)
        {
            this.action = action;
        }

        @Override
        public void perform(MouseEvent e)
        {
            gamePanel.setAction(action);
        }
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

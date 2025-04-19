package org.exolin.citysim;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.exolin.citysim.bt.BuildingTypes;
import org.exolin.citysim.model.World;
import org.exolin.citysim.model.Worlds;
import org.exolin.citysim.ui.GameControlPanel;
import org.exolin.citysim.ui.GamePanel;
import org.exolin.citysim.ui.GamePanelListener;
import org.exolin.citysim.ui.KeyMapping;
import org.exolin.citysim.ui.LoadGame;
import org.exolin.citysim.ui.actions.Action;
import org.exolin.citysim.ui.budget.BudgetWindow;
import org.exolin.citysim.ui.sp.SelectorPanel;

/**
 *
 * @author Thomas
 */
public class Main
{
    private static JFrame createSelector(SelectorPanel sp)
    {
        JFrame selector = new JFrame("Selector");
        selector.setLayout(new BorderLayout());
        selector.add(sp, BorderLayout.CENTER);
        selector.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        selector.setSize(300, 700);
        selector.setAlwaysOnTop(true);
        return selector;
    }
    
    public static KeyMapping createKeyListener(BudgetWindow bw, GamePanel gp, JFrame selector, GameControlPanel gd)
    {
        KeyMapping mapping = new KeyMapping();
        mapping.add(KeyEvent.VK_ESCAPE, "Select no tool", () -> gp.setAction(Action.NONE));
        mapping.add(KeyEvent.VK_PAGE_UP, "Rotate left", () -> gp.setRotation(gp.getRotation().getPrev()));
        mapping.add(KeyEvent.VK_PAGE_DOWN, "Rotate right", () -> gp.setRotation(gp.getRotation().getNext()));
        mapping.add(KeyEvent.VK_F6, "Toggle budget window", () -> {
                bw.update(gp.getWorld());
                bw.setVisible(!bw.isVisible());
            });
        mapping.add(KeyEvent.VK_F7, "Toggle debug", () -> gp.toggleDebug());
        mapping.add(KeyEvent.VK_F8, "Go to next view", () -> gp.setView(gp.getView().getNext()));
        mapping.add(KeyEvent.VK_F10, "Toggle selector visibility", () -> selector.setVisible(!selector.isVisible()));
        mapping.add(KeyEvent.VK_F11, "Toggle color grid", () -> gp.toggleColorGrid());
        mapping.add(KeyEvent.VK_F12, "Toggle control panel", () -> gd.setVisible(!gd.isVisible()));
        
        gd.setKeyMapping(mapping);
        
        return mapping;
    }
    
    public static void main(String[] args)
    {
        LoadGame lg = new LoadGame(Worlds.all(), w -> {
            if(w == null)
                System.exit(0);
            else
                play(w);
        });
        lg.setVisible(true);
    }
    
    public static void play(World world)
    {
        BuildingTypes.init();
        
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("City Sim - "+world.getName());
        f.setLayout(new BorderLayout());
        GameControlPanel gd = new GameControlPanel();
        f.add(gd, BorderLayout.NORTH);
        
        SelectorPanel sp = new SelectorPanel();
        
        GamePanel gp = new GamePanel(world, f, new GamePanelListener()
        {
            @Override
            public void created(GamePanel panel)
            {
                gd.setPanel(panel);
                sp.setPanel(panel);
            }

            @Override
            public void onActionChanged(Action newAction)
            {
                sp.setAction(newAction);
            }
        });
        f.add(gp, BorderLayout.CENTER);
        
        for(Map.Entry<String, List<Action>> e: gp.getActions().entrySet())
        {
            for(Action a: e.getValue())
                sp.add(e.getKey(), a);
        }
        
        JFrame selector = createSelector(sp);
        selector.setVisible(true);
        
        sp.doneAdding();
        
        f.setSize(640, 480);
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        BudgetWindow bw = new BudgetWindow(f);
        f.addKeyListener(createKeyListener(bw, gp, selector, gd));
        
        gp.start();
        f.setVisible(true);
    }
}

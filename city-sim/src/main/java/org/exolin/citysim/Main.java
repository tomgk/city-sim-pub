package org.exolin.citysim;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import org.exolin.citysim.bt.BuildingTypes;
import org.exolin.citysim.ui.Action;
import org.exolin.citysim.ui.GameControlPanel;
import org.exolin.citysim.ui.GamePanel;
import org.exolin.citysim.ui.GamePanelListener;
import org.exolin.citysim.ui.sp.SelectorPanel;

/**
 *
 * @author Thomas
 */
public class Main
{
    public static void main(String[] args)
    {
        BuildingTypes.init();
        
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("City Sim");
        f.setLayout(new BorderLayout());
        GameControlPanel gd = new GameControlPanel();
        f.add(gd, BorderLayout.NORTH);
        
        SelectorPanel sp = new SelectorPanel();
        
        GamePanel gp = new GamePanel(Worlds.World2(), f, new GamePanelListener()
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
        
        JFrame selector = new JFrame("Selector");
        selector.setLayout(new BorderLayout());
        selector.add(sp, BorderLayout.CENTER);
        selector.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        selector.setSize(300, 500);
        selector.setVisible(true);
        
        f.setSize(640, 480);
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        f.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                switch(e.getKeyCode())
                {
                    case KeyEvent.VK_ESCAPE -> gp.setAction(Action.NONE);
                    case KeyEvent.VK_F8 -> gp.setView(gp.getView().getNext());
                    case KeyEvent.VK_F9 -> gp.toggleDebug();
                    case KeyEvent.VK_F10 -> selector.setVisible(!selector.isVisible());
                    case KeyEvent.VK_F11 -> gp.toggleColorGrid();
                    case KeyEvent.VK_F12 -> gd.setVisible(!gd.isVisible());
                }
            }
        });
        
        gp.start();
        f.setVisible(true);
    }
}

package org.exolin.citysim;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
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
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setTitle("City Sim");
        f.setLayout(new BorderLayout());
        GameControlPanel gd = new GameControlPanel();
        f.add(gd, BorderLayout.NORTH);
        GamePanel gp = new GamePanel(World.World2(), f, new GamePanelListener()
        {
            @Override
            public void created(GamePanel panel)
            {
                gd.setPanel(panel);
            }
        });
        f.add(gp, BorderLayout.CENTER);
        
        SelectorPanel sp = new SelectorPanel(gp);
        
        for(Map.Entry<String, List<Action>> e: gp.getActions().entrySet())
        {
            for(Action a: e.getValue())
                sp.add(e.getKey(), a);
        }
        
        JScrollPane scroll = new JScrollPane(sp);
        JFrame selector = new JFrame("Selector");
        selector.setLayout(new BorderLayout());
        selector.add(scroll, BorderLayout.CENTER);
        selector.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                    case KeyEvent.VK_F11:
                        gp.toggleColorGrid();
                        break;
                        
                    case KeyEvent.VK_F12:
                        gd.setVisible(!gd.isVisible());
                        break;
                }
            }
        });
        
        gp.start();
        f.setVisible(true);
    }
}

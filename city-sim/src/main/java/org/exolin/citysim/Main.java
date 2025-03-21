package org.exolin.citysim;

import org.exolin.citysim.ui.GameDataPanel;
import org.exolin.citysim.ui.Action;
import org.exolin.citysim.ui.sp.SelectorPanel;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.exolin.citysim.ui.GamePanel;

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
        GameDataPanel gd = new GameDataPanel();
        f.add(gd, BorderLayout.NORTH);
        GamePanel gp = new GamePanel(World.World2(), f, gd);
        f.add(gp, BorderLayout.CENTER);
        
        SelectorPanel sp = new SelectorPanel(gp);
        
        for(Action a: gp.getActions())
        {
            if(a instanceof Action.NoAction)
                continue;
            
            sp.add(a);
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
                if(e.getKeyCode() == KeyEvent.VK_F12)
                    gd.setVisible(!gd.isVisible());
            }
        });
        
        f.setVisible(true);
    }
}

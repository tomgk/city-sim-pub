package org.exolin.citysim;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.JFrame;

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
        GameData gd = new GameData();
        f.add(gd, BorderLayout.NORTH);
        f.add(new GamePanel(World.World2(), f, gd), BorderLayout.CENTER);
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

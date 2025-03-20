package org.exolin.citysim;

import java.awt.BorderLayout;
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
        f.add(new GamePanel(f, gd), BorderLayout.CENTER);
        f.setSize(640, 480);
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
    }
}

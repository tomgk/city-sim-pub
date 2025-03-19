package org.exolin.citysim;

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
        f.add(new GamePanel());
        f.setSize(640, 480);
        f.setExtendedState(f.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        f.setVisible(true);
    }
}

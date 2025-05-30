package org.exolin.citysim.ui.sp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.exolin.citysim.model.RCI;

/**
 *
 * @author Thomas
 */
public class RCIPanel extends JPanel
{
    private int r;
    private int c;
    private int i;

    public RCIPanel(int r, int c, int i)
    {
        this.r = r;
        this.c = c;
        this.i = i;
        
        setPreferredSize(new Dimension(30, 90));
    }

    void set(RCI rci)
    {
        this.r = rci.getR();
        this.c = rci.getC();
        this.i = rci.getI();
        repaint();
    }
    
    private final Image icon = new ImageIcon(RCIPanel.class.getResource("/org/exolin/citysim/menu/rci_label.png")).getImage();
    
    @Override
    public void paint(Graphics g)
    {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.drawImage(icon, 2, 31, this);
        drawValue(g, 0, r, Color.GREEN);
        drawValue(g, 1, c, Color.BLUE);
        drawValue(g, 2, i, Color.YELLOW.darker());
    }

    private void drawValue(Graphics g, int index, int value, Color color)
    {
        int x = 5 + index * 8;
        if(value > 0)
        {
            int y = 31;

            int h = 31 * value / 100;
            //System.out.println("h="+h);
            y -= h;

            g.setColor(color);
            g.fillRect(x, y, 3, h);
        }
        else if(value < 0)
        {
            int y = 46;

            int h = 31 * -value / 100;
            //System.out.println("h="+h);

            g.setColor(color);
            g.fillRect(x, y, 3, h);
        }
    }
    
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.add(new RCIPanel(50, -30, 70), BorderLayout.CENTER);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

package org.exolin.citysim.ui.sp;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Thomas
 */
public class SelectorPanel3 extends JPanel
{
    public SelectorPanel3()
    {
        setLayout(new GridBagLayout());
        
        x = 0;
        registerButton("bulldoze.png");
        registerButton("tree_water.png");
        registerButton("emergency.png");
        ++y;

        x = 0;
        registerButton("electricity.png");
        registerButton("water.png");
        registerButton("city_hall.png");
        ++y;

        x = 0;
        registerButton("street.png");
        registerButton("rail.png");
        registerButton("port.png");
        ++y;

        x = 0;
        registerButton("residential.png");
        registerButton("business.png");
        registerButton("industry.png");
        ++y;

        x = 0;
        registerButton("school.png");
        registerButton("police.png");
        registerButton("party.png");
        ++y;
        
        w = 3;
        x = 0;
        registerButton("sign.png");
        registerButton("info.png");
        ++y;

        w = 3;
        x = 0;
        registerButton("turn_left.png");
        registerButton("turn_right.png");
        ++y;

        w = 2;
        x = 0;
        registerButton("zoom_out.png");
        registerButton("zoom_in.png");
        registerButton("center.png");
        ++y;
    }
    
    private int x = 0;
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

    private void registerButton(String path)
    {
        JButton button = new javax.swing.JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource("/org/exolin/citysim/menu/"+path));
        button.setIcon(icon);
        
        GridBagConstraints constraints = createConstraints(x, y, w, 2);
        
        add(button, constraints);
        
        //System.out.println(x+","+y);
        
        x += w;
        if(x >= 6)
        {
            x = 0;
            ++y;
        }
    }
    
    public static void main(String[] args)
    {
        JFrame f = new JFrame();
        f.add(new SelectorPanel3(), BorderLayout.CENTER);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

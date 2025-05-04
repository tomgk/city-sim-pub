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
        registerButton("/org/exolin/citysim/menu/bulldoze.png");
        registerButton("/org/exolin/citysim/menu/tree_water.png");
        registerButton("/org/exolin/citysim/menu/emergency.png");
        ++y;

        x = 0;
        registerButton("/org/exolin/citysim/menu/electricity.png");
        registerButton("/org/exolin/citysim/menu/water.png");
        registerButton("/org/exolin/citysim/menu/city_hall.png");
        ++y;

        x = 0;
        registerButton("/org/exolin/citysim/menu/street.png");
        registerButton("/org/exolin/citysim/menu/rail.png");
        registerButton("/org/exolin/citysim/menu/port.png");
        ++y;

        x = 0;
        registerButton("/org/exolin/citysim/menu/residential.png");
        registerButton("/org/exolin/citysim/menu/business.png");
        registerButton("/org/exolin/citysim/menu/industry.png");
        ++y;

        x = 0;
        registerButton("/org/exolin/citysim/menu/school.png");
        registerButton("/org/exolin/citysim/menu/police.png");
        registerButton("/org/exolin/citysim/menu/party.png");
        ++y;
        
        w = 3;
        x = 0;
        registerButton("/org/exolin/citysim/menu/sign.png");
        registerButton("/org/exolin/citysim/menu/info.png");
        ++y;

        w = 3;
        x = 0;
        registerButton("/org/exolin/citysim/menu/turn_left.png");
        registerButton("/org/exolin/citysim/menu/turn_right.png");
        ++y;

        w = 2;
        x = 0;
        registerButton("/org/exolin/citysim/menu/zoom_out.png");
        registerButton("/org/exolin/citysim/menu/zoom_in.png");
        registerButton("/org/exolin/citysim/menu/center.png");
        ++y;
    }
    
    private int x = 0;
    private int y = 0;
    private int w = 2;

    private void registerButton(String path)
    {
        JButton button = new javax.swing.JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        button.setIcon(icon);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = w;
        constraints.gridheight = 2;
        
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

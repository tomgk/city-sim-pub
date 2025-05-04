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
        
        registerButton("/org/exolin/citysim/menu/bulldoze.png");
        registerButton("/org/exolin/citysim/menu/tree_water.png");
        registerButton("/org/exolin/citysim/menu/emergency.png");

        registerButton("/org/exolin/citysim/menu/electricity.png");
        registerButton("/org/exolin/citysim/menu/water.png");
        registerButton("/org/exolin/citysim/menu/city_hall.png");

        registerButton("/org/exolin/citysim/menu/street.png");
        registerButton("/org/exolin/citysim/menu/rail.png");
        registerButton("/org/exolin/citysim/menu/port.png");

        registerButton("/org/exolin/citysim/menu/residential.png");
        registerButton("/org/exolin/citysim/menu/business.png");
        registerButton("/org/exolin/citysim/menu/industry.png");

        registerButton("/org/exolin/citysim/menu/school.png");
        registerButton("/org/exolin/citysim/menu/police.png");
        registerButton("/org/exolin/citysim/menu/party.png");

        w = 2;
        registerButton("/org/exolin/citysim/menu/sign.png");
        registerButton("/org/exolin/citysim/menu/info.png");

        w = 2;
        registerButton("/org/exolin/citysim/menu/turn_left.png");
        registerButton("/org/exolin/citysim/menu/turn_right.png");
    }
    
    private int x = 0;
    private int y = 0;
    private int w = 3;

    private void registerButton(String path)
    {
        JButton button = new javax.swing.JButton();
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        button.setIcon(icon);
        
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = x;
        constraints.gridy = y;
        constraints.gridwidth = 1;
        constraints.gridheight = 1;
        
        add(button, constraints);
        
        ++x;
        if(x >= w)
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

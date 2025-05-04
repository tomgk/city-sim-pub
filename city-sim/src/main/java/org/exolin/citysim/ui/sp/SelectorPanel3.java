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
        
        registerButton(0, "bulldoze.png");
        registerButton(2, "tree_water.png");
        registerButton(4, "emergency.png");
        ++y;
        
        registerButton(0, "electricity.png");
        registerButton(2, "water.png");
        registerButton(4, "city_hall.png");
        ++y;
        
        registerButton(0, "street.png");
        registerButton(2, "rail.png");
        registerButton(4, "port.png");
        ++y;
        
        registerButton(0, "residential.png");
        registerButton(2, "business.png");
        registerButton(4, "industry.png");
        ++y;
        
        registerButton(0, "school.png");
        registerButton(2, "police.png");
        registerButton(4, "party.png");
        ++y;
        
        w = 3;
        registerButton(0, "sign.png");
        registerButton(3, "info.png");
        ++y;

        w = 3;
        registerButton(0, "turn_left.png");
        registerButton(3, "turn_right.png");
        ++y;

        w = 2;
        registerButton(0, "zoom_out.png");
        registerButton(2, "zoom_in.png");
        registerButton(4, "center.png");
        ++y;
    }
    
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

    private void registerButton(int x, String path)
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

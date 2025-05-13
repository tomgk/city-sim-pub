package org.exolin.citysim.ui.sp;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import org.exolin.citysim.model.Worlds;
import org.exolin.citysim.ui.GamePanel;
import org.exolin.citysim.ui.budget.BudgetWindow;

/**
 *
 * @author Thomas
 */
public class SelectorPanel3Test
{
    public static void main(String[] args)
    {
        GamePanel gp = new GamePanel(Worlds.World1(), new JFrame(), new EmptyGamePanelListener(), new BudgetWindow(new JFrame()));
        JFrame f = new JFrame();
        SelectorPanel3 sp = new SelectorPanel3(new BudgetWindow(f));
        sp.setGamePanel(gp);
        f.add(sp, BorderLayout.CENTER);
        f.pack();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }
}

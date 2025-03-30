package org.exolin.citysim.ui;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;
import javax.swing.JOptionPane;

/**
 *
 * @author Thomas
 */
public class ErrorDisplay
{
    static void show(Component parent, Exception e)
    {
        e.printStackTrace(System.out);
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        JOptionPane.showMessageDialog(parent, out.toString(), "Unexpected Exception", JOptionPane.ERROR_MESSAGE);
    }
}

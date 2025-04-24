package org.exolin.citysim.ui;

import java.awt.Component;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author Thomas
 */
public class ErrorDisplay
{
    private static final Map<String, Long> oneTimeErrors = new LinkedHashMap<>();
    
    @SuppressWarnings("ThrowableResultIgnored")
    public static void show(Component parent, Exception e)
    {
        Objects.requireNonNull(parent);
        Objects.requireNonNull(e);
        
        if(e instanceof OutOfGridException)
            return;
        
        e.printStackTrace(System.out);
        StringWriter out = new StringWriter();
        e.printStackTrace(new PrintWriter(out));
        JOptionPane.showMessageDialog(parent, out.toString(), "Unexpected Exception", JOptionPane.ERROR_MESSAGE);
    }
    
    public static void showOneTimeError(String message)
    {
        Long last = oneTimeErrors.get(message);
        long now = System.currentTimeMillis();
        //show every minute
        if(last != null && now - last < 60000)
            return;
        
        oneTimeErrors.put(message, now);
        
        UnsupportedOperationException e = new UnsupportedOperationException(message);
        show(null, e);
    }
}

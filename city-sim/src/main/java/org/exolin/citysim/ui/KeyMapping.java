package org.exolin.citysim.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 *
 * @author Thomas
 */
public class KeyMapping extends KeyAdapter
{
    private final Map<Integer, Runnable> actions = new HashMap<>();
    private final Map<Integer, String> description = new TreeMap<>();
    
    public void add(int key, String title, Runnable r)
    {
        if(actions.putIfAbsent(key, r) != null)
            throw new IllegalStateException();
        
        description.put(key, title);
    }
    
    private static final Runnable NOTHING = () -> {};
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        actions.getOrDefault(e.getKeyCode(), NOTHING).run();
    }

    public String getHTML()
    {
        return "<html>"+description.entrySet()
                .stream()
                .map(e -> "<b>"+KeyEvent.getKeyText(e.getKey())+"</b>: "+e.getValue())
                .collect(Collectors.joining(", "))+"</html>";
    }
}

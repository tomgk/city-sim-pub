package org.exolin.citysim.ui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 *
 * @author Thomas
 */
public class KeyMapping extends KeyAdapter
{
    private final Map<Integer, Runnable> actions = new LinkedHashMap<>();
    private final Map<Integer, String> description = new LinkedHashMap<>();
    
    public void add(int key, /*String title, */Runnable r)
    {
        if(actions.putIfAbsent(key, r) != null)
            throw new IllegalStateException();
        
        //description.put(key, title);
    }
    
    private static final Runnable NOTHING = () -> {};
    
    @Override
    public void keyPressed(KeyEvent e)
    {
        actions.getOrDefault(e.getKeyCode(), NOTHING).run();
    }
}

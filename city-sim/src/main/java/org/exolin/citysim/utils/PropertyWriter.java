package org.exolin.citysim.utils;

import java.util.Optional;

/**
 *
 * @author Thomas
 */
public class PropertyWriter
{
    private final StringBuilder sb = new StringBuilder();
    private boolean first = true;

    public PropertyWriter(String type)
    {
        sb.append(type).append("[");
    }
    
    public void addOptional(String name, boolean value)
    {
        if(value)
            add(name, (Object)value);
    }
    
    public void add(String name, int value)
    {
        add(name, (Object)value);
    }
    
    public void add(String name, String value)
    {
        add(name, (Object)value);
    }
    
    private void add(String name, Object value)
    {
        if(!first)
            sb.append(",");
        else
            first = true;
        
        sb.append(name).append("=").append(value);
    }
    
    public void addOptional(String name, Optional<?> value)
    {
        value.ifPresent(v -> add(name, v));
    }

    public void finish()
    {
        sb.append("]");
    }

    @Override
    public String toString()
    {
        return sb.toString();
    }
}

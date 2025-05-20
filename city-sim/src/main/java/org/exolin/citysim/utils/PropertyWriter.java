package org.exolin.citysim.utils;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * Helper class to implement {@link Object#toString()}
 * @author Thomas
 */
public class PropertyWriter
{
    private final StringBuilder sb;
    private boolean first = true;
    private boolean finished = false;

    public PropertyWriter(String type)
    {
        sb = new StringBuilder();
        sb.append(type).append("[");
    }
    
    public void addSubObject(String name, String type, Consumer<PropertyWriter> writer)
    {
        maybeAddComma();
        sb.append(name).append("=").append(type).append("[");
        writer.accept(this);
        sb.append("]");
    }
    
    private void maybeAddComma()
    {
        if(finished)
            throw new IllegalStateException();
        
        if(!first)
            sb.append(",");
        else
            first = false;
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
        if(value instanceof Optional)
            throw new IllegalArgumentException();
        
        maybeAddComma();
        
        sb.append(name).append("=").append(value);
    }
    
    public void addOptional(String name, Optional<?> value)
    {
        value.ifPresent(v -> add(name, v));
    }

    public void addOptionalExplicit(String name, Optional<?> value)
    {
        value.ifPresentOrElse(v -> add(name, v), () -> add(name, "(none)"));
    }

    public void finish()
    {
        if(finished)
            throw new IllegalStateException("already finished");
        
        sb.append("]");
        finished = true;
    }

    @Override
    public String toString()
    {
        if(!finished)
            throw new IllegalStateException("not finished yet");
        
        return sb.toString();
    }
}

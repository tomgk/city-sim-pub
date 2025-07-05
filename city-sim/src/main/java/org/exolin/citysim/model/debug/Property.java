package org.exolin.citysim.model.debug;

import java.util.Map;

/**
 *
 * @author Thomas
 * @param <T>
 */
public class Property<T> implements Map.Entry<String, T>
{
    private final String name;
    private final Value<T> value;

    public Property(String name, Value<T> value)
    {
        this.name = name;
        this.value = value;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public T getValue()
    {
        return value.get();
    }

    public void set(T value)
    {
        this.value.set(value);
    }

    public boolean isReadonly()
    {
        return value.isReadonly();
    }

    public Class<T> getType()
    {
        return value.getType();
    }

    @Override
    public String getKey()
    {
        return name;
    }

    @Override
    public T setValue(T value)
    {
        T old = this.value.get();
        set(value);
        return old;
    }
}

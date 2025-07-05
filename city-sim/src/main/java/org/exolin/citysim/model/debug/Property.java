package org.exolin.citysim.model.debug;

import java.util.Map;

/**
 *
 * @author Thomas
 * @param <T>
 */
public class Property<T> implements Map.Entry<String, Value<T>>
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
    public Value<T> getValue()
    {
        return value;
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
    public Value<T> setValue(Value<T> value)
    {
        throw new UnsupportedOperationException();
    }
}

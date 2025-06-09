package org.exolin.citysim.model.debug;

/**
 *
 * @author Thomas
 */
public interface ReadonlyValue<T> extends Value<T>
{
    @Override
    default boolean isReadonly()
    {
        return true;
    }

    @Override
    public default void set(T value)
    {
        throw new UnsupportedOperationException("readonly");
    }
}

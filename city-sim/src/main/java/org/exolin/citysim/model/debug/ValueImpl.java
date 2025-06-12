package org.exolin.citysim.model.debug;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 *
 * @author Thomas
 * @param <T>
 */
public class ValueImpl<T> implements Value<T>
{
    private final Supplier<T> getter;
    private final Consumer<T> setter;

    public ValueImpl(Supplier<T> getter, Consumer<T> setter)
    {
        this.getter = Objects.requireNonNull(getter);
        this.setter = Objects.requireNonNull(setter);
    }

    @Override
    public T get()
    {
        return getter.get();
    }

    @Override
    public void set(T value)
    {
        setter.accept(value);
    }
}

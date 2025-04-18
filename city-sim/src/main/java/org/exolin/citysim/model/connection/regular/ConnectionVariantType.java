package org.exolin.citysim.model.connection.regular;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 * @param <T>
 */
public class ConnectionVariantType<T extends ConnectionVariant>
{
    private final List<ConnectionVariant> values;

    public ConnectionVariantType(T v1, T v2, T v3, T v4)
    {
        this.values = Arrays.asList(v1, v2, v3, v4);
        Class<?> t = v1.getClass();
        for(ConnectionVariant v : values)
        {
            Objects.requireNonNull(v);
            if(t != v.getClass())
                throw new IllegalArgumentException("wrong type");
        }
    }

    public ConnectionVariant rotate(ConnectionVariant base, Rotation rotation)
    {
        int pos = values.indexOf(base);
        if(pos == -1)
            throw new IllegalArgumentException();

        pos += rotation.getAmount();
        if(pos >= values.size())
            pos -= values.size();

        return values.get(pos);
    }
}

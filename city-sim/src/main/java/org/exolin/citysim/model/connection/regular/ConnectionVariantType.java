package org.exolin.citysim.model.connection.regular;

import java.util.Arrays;
import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 * @param <T>
 */
public class ConnectionVariantType<T extends ConnectionVariant>
{
    private final ConnectionVariant[] values;

    public ConnectionVariantType(T v1, T v2, T v3, T v4)
    {
        this.values = new ConnectionVariant[]{v1, v2, v3, v4};
    }

    public Object[] getValues()
    {
        return values.clone();
    }

    public ConnectionVariant rotate(ConnectionVariant base, Rotation rotation)
    {
        int pos = Arrays.asList(values).indexOf(base);
        if(pos == -1)
            throw new IllegalArgumentException();

        pos += rotation.getAmount();
        if(pos >= values.length)
            pos -= values.length;

        return values[pos];
    }
}

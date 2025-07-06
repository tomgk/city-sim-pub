package org.exolin.citysim.model.connection.regular;

import java.util.Optional;
import org.exolin.citysim.model.Rotation;

/**
 *
 * @author Thomas
 */
public enum End implements ConnectionVariant
{
    NORTH("^"),
    EAST(">"),
    SOUTH("v"),
    WEST("<");
    
    private final String info;

    private End(String info)
    {
        this.info = info;
    }
    
    private static final ConnectionVariantType<End> TYPE =
            new ConnectionVariantType<>(SOUTH, EAST, NORTH, WEST);
    
    @Override
    public ConnectionVariant rotate(Rotation rotation)
    {
        //return StreetVariant.rotate(this, rotation,
        //                NORTH, EAST, SOUTH, WEST);
        return TYPE.rotate(this, rotation);
    }

    @Override
    public Optional<String> getInfo()
    {
        return Optional.of(info);
    }
}

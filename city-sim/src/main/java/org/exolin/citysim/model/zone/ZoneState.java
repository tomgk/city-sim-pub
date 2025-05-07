package org.exolin.citysim.model.zone;

import java.util.Objects;
import org.exolin.citysim.utils.PropertyWriter;

/**
 *
 * @author Thomas
 */
public record ZoneState(ZoneType type, ZoneType.Variant variant)
{
    public ZoneState
    {
        Objects.requireNonNull(type);
        Objects.requireNonNull(variant);
    }

    public void write(PropertyWriter writer)
    {
        writer.add("zone", type.getName());
        writer.add("zone", variant.name());
    }
}

package org.exolin.citysim.model.zone;

import java.util.Objects;

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
}

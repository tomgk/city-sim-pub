package org.exolin.citysim.model.zone;

import java.util.Objects;

/**
 *
 * @author Thomas
 */
public record ZoneState(ZoneType type)
{
    public ZoneState
    {
        Objects.requireNonNull(type);
    }
}

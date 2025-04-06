package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public class Zone extends Building
{
    public Zone(ZoneType type, int x, int y, ZoneType.Variant variant)
    {
        super(type, x, y, variant);
    }
}

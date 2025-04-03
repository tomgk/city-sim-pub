package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public interface BuildingVariant
{
    default int index()
    {
        return ((Enum)this).ordinal();
    }
    String name();
}

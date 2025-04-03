package org.exolin.citysim;

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

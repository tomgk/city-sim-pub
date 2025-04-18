package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public interface StructureVariant
{
    default int index()
    {
        return ((Enum)this).ordinal();
    }
    String name();
}

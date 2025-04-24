package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 * @param <T>
 */
public interface StructureData<T extends StructureData<T>>
{
    T copy();
}

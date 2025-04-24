package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 * @param <T>
 */
public interface StructureParameters<T extends StructureParameters<T>>
{
    T copy();
}

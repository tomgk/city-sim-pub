package org.exolin.citysim.model;

/**
 * Holds additional data for a structure besides the basics
 * (type, coordinates and variant).
 * 
 * @author Thomas
 * @param <T>
 */
public interface StructureParameters<T extends StructureParameters<T>>
{
    /**
     * Returns a copy.
     * 
     * If the object has no state it is allowed to share instances,
     * otherwise it is not allowed.
     * 
     * @return a logical copy
     */
    T copy();
}

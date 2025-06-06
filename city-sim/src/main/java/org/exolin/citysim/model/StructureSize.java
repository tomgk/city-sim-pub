package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public enum StructureSize
{
    _1, _2, _3, _4;
    
    public int toInteger()
    {
        return ordinal()+1;
    }
}

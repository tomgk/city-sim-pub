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
    
    public static final int MAX = values().length;

    @Override
    public String toString()
    {
        return StructureSize.class.getSimpleName()+"["+toInteger()+"x"+toInteger()+"]";
    }
}

package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public class EmptyStructureParameters implements StructureParameters<EmptyStructureParameters>
{
    private static final EmptyStructureParameters INSTANCE = new EmptyStructureParameters();
    
    private EmptyStructureParameters(){}

    public static EmptyStructureParameters getInstance()
    {
        return INSTANCE;
    }
    
    @Override
    public EmptyStructureParameters copy()
    {
        return INSTANCE;
    }
}

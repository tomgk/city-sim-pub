package org.exolin.citysim.model;

/**
 * An implementation of {@link StructureParameters} for when there is no need
 * for additional data for a {@link Structure}.
 * 
 * @author Thomas
 */
public final class EmptyStructureParameters implements StructureParameters<EmptyStructureParameters>
{
    private static final EmptyStructureParameters INSTANCE = new EmptyStructureParameters();
    
    private EmptyStructureParameters()
    {
        if(INSTANCE != null)
            throw new IllegalStateException("only allowed once");
    }

    /**
     * Returns the one instance.
     * 
     * Given that the class holds no state the same instance can be shared
     * 
     * @return the instance
     */
    public static EmptyStructureParameters getInstance()
    {
        return INSTANCE;
    }
    
    /**
     * Returns the same instance, because there is no state, so the same object
     * can be shared without issues.
     * 
     * @return {@link EmptyStructureParameters#getInstance()}
     */
    @Override
    public EmptyStructureParameters copy()
    {
        return INSTANCE;
    }

    @Override
    public void writeAdditional(StringBuilder sb)
    {
        
    }
}

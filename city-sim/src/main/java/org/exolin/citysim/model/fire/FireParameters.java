package org.exolin.citysim.model.fire;

import org.exolin.citysim.model.StructureParameters;

/**
 *
 * @author Thomas
 */
public class FireParameters implements StructureParameters<FireParameters>
{
    int remainingLife;

    public FireParameters()
    {
        this(10000);
    }

    public FireParameters(int remainingLife)
    {
        this.remainingLife = remainingLife;
    }
    

    @Override
    public FireParameters copy()
    {
        return new FireParameters(remainingLife);
    }
}

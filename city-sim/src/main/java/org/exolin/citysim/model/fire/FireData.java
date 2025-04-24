package org.exolin.citysim.model.fire;

import org.exolin.citysim.model.StructureParameters;

/**
 *
 * @author Thomas
 */
public class FireData implements StructureParameters<FireData>
{
    int remainingLife;

    public FireData()
    {
        this(10000);
    }

    public FireData(int remainingLife)
    {
        this.remainingLife = remainingLife;
    }
    

    @Override
    public FireData copy()
    {
        return new FireData(remainingLife);
    }
}

package org.exolin.citysim.model.building.vacant;

import org.exolin.citysim.model.StructureParameters;

/**
 *
 * @author Thomas
 */
public class VacantParameters implements StructureParameters<VacantParameters>
{
    @Override
    public VacantParameters copy()
    {
        return new VacantParameters();
    }
}

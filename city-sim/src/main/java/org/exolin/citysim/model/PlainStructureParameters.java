package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public class PlainStructureParameters implements StructureParameters<PlainStructureParameters>
{
    @Override
    public PlainStructureParameters copy()
    {
        return new PlainStructureParameters();
    }
}

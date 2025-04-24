package org.exolin.citysim.model;

/**
 *
 * @author Thomas
 */
public class PlainStructureData implements StructureData<PlainStructureData>
{
    @Override
    public PlainStructureData copy()
    {
        return new PlainStructureData();
    }
}

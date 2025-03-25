package org.exolin.citysim;

/**
 *
 * @author Thomas
 */
public class ActualBuilding extends Building
{
    public ActualBuilding(ActualBuildingType type, int x, int y, ActualBuildingType.Variant version)
    {
        super(type, x, y, version);
    }

    @Override
    public ActualBuildingType getType()
    {
        return (ActualBuildingType)super.getType();
    }
}

package org.exolin.citysim.storage;

import org.exolin.citysim.Street;
import org.exolin.citysim.StreetType;

/**
 *
 * @author Thomas
 */
public class StreetData extends BuildingData
{
    public StreetData(Street b)
    {
        super(b);
    }

    @Override
    public Class<? extends Enum<?>> getVariantClass()
    {
        return StreetType.Variant.class;
    }
}

package org.exolin.citysim.model.street.cross;

import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.BuildingType;
import org.exolin.citysim.model.BuildingVariant;
import org.exolin.citysim.model.street.StreetType;

/**
 *
 * @author Thomas
 */
public class CrossConnectionType extends BuildingType<CrossConnection, CrossConnectionType.Variant>
{
    public enum Variant implements BuildingVariant
    {
        DEFAULT
    }
    
    private final StreetType type1;
    private final StreetType type2;

    public CrossConnectionType(String name, Animation animation, int size, StreetType type1, StreetType type2)
    {
        super(name, animation, size);
        this.type1 = Objects.requireNonNull(type1);
        this.type2 = Objects.requireNonNull(type2);
    }

    @Override
    public CrossConnection createBuilding(int x, int y, Variant variant)
    {
        return new CrossConnection(this, x, y, variant);
    }

    public StreetType getType1()
    {
        return type1;
    }

    public StreetType getType2()
    {
        return type2;
    }
    
    @Override
    public Variant getDefaultVariant()
    {
        return Variant.DEFAULT;
    }
}

package org.exolin.citysim.model.building.vacant;

import java.util.Objects;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.zone.ZoneType;

/**
 *
 * @author Thomas
 */
public class VacantType extends StructureType<Vacant, VacantType.Variant, EmptyStructureParameters>
{
    public enum Variant implements StructureVariant
    {
        DEFAULT
    }
    
    private final ZoneType zoneType;
    private final boolean destroyed;

    @SuppressWarnings("LeakingThisInConstructor")
    public VacantType(String name, Animation animation, int size, ZoneType zoneType, boolean destroyed)
    {
        super(name, animation, size);
        this.zoneType = Objects.requireNonNull(zoneType);
        this.destroyed = destroyed;
        zoneType.addVacant(this);
    }

    public boolean isDestroyed()
    {
        return destroyed;
    }
    
    public ZoneType getZoneType()
    {
        return zoneType;
    }

    @Override
    public VacantType.Variant getVariantForDefaultImage()
    {
        return VacantType.Variant.DEFAULT;
    }

    @Override
    public Vacant createBuilding(int x, int y, Variant variant, EmptyStructureParameters data)
    {
        return new Vacant(this, x, y, variant, data);
    }
}
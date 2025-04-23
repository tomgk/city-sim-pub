package org.exolin.citysim.model.fire;

import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class FireType extends StructureType<Fire, FireType.Variant>
{
    public enum Variant implements StructureVariant
    {
        DEFAULT
    }
    
    public FireType(String name, Animation animation, int size)
    {
        super(name, animation, size);
    }

    @Override
    public Fire createBuilding(int x, int y, Variant variant)
    {
        return new Fire(this, x, y, variant);
    }

    @Override
    public Variant getDefaultVariant()
    {
        return Variant.DEFAULT;
    }
}

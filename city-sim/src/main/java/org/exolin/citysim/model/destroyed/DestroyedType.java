package org.exolin.citysim.model.destroyed;

import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.PlainStructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class DestroyedType extends StructureType<Destroyed, DestroyedType.Variant, PlainStructureParameters>
{
    public enum Variant implements StructureVariant
    {
        DEFAULT
    }

    public DestroyedType(String name, Animation animation, int size)
    {
        super(name, animation, size);
    }

    @Override
    public Destroyed createBuilding(int x, int y, Variant variant, PlainStructureParameters data)
    {
        return new Destroyed(this, x, y, variant, data);
    }

    @Override
    public Variant getDefaultVariant()
    {
        return Variant.DEFAULT;
    }
}

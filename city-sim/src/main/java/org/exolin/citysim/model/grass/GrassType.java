package org.exolin.citysim.model.grass;

import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.EmptyStructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class GrassType extends StructureType<Grass, GrassType.Variant, EmptyStructureParameters>
{
    public GrassType(String name, Animation animation, int size)
    {
        super(name, animation, size);
    }
    
    public enum Variant implements StructureVariant
    {
        DEFAULT
    }

    @Override
    public Grass createBuilding(int x, int y, Variant variant, EmptyStructureParameters data)
    {
        return new Grass(this, x, y, variant, data);
    }

    @Override
    public Variant getVariantForDefaultImage()
    {
        return Variant.DEFAULT;
    }
}

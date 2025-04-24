package org.exolin.citysim.model.fire;

import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import org.exolin.citysim.model.PlainStructureData;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class FireType extends StructureType<Fire, FireType.Variant, PlainStructureData>
{
    public static final FireType fire = new FireType("fire", createAnimation("destruction/fire", 4, 500), 1);
    
    public enum Variant implements StructureVariant
    {
        DEFAULT;
    }
    
    public FireType(String name, Animation animation, int size)
    {
        super(name, animation, size);
    }

    @Override
    public Fire createBuilding(int x, int y, Variant variant, PlainStructureData data)
    {
        return new Fire(this, x, y, variant, data);
    }

    @Override
    public Variant getDefaultVariant()
    {
        return Variant.DEFAULT;
    }
}

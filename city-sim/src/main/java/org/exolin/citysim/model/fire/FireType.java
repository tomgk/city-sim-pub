package org.exolin.citysim.model.fire;

import java.util.List;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class FireType extends StructureType<Fire, FireType.Variant, FireParameters>
{
    private static final Animation fireAnimation = createAnimation("destruction/fire", 16, 500);
    
    public static final FireType fire = new FireType("fire", List.of(
            fireAnimation,
            fireAnimation.createRotated("destruction/fire2", 1),
            fireAnimation.createRotated("destruction/fire3", 2),
            fireAnimation.createRotated("destruction/fire4", 3)
    ), 1);
    
    public enum Variant implements StructureVariant
    {
        V1,
        V2,
        V3,
        V4;
        
        private static final Variant[] values = values();
        
        public static Variant random()
        {
            return values[(int)(Math.random()*values.length)];
        }
    }
    
    public FireType(String name, List<Animation> animation, int size)
    {
        super(name, animation, size);
    }

    @Override
    public Fire createBuilding(int x, int y, Variant variant, FireParameters data)
    {
        return new Fire(this, x, y, variant, data);
    }

    @Override
    public Variant getVariantForDefaultImage()
    {
        return Variant.V1;
    }
}

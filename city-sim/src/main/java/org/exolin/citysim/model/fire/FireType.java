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
            fireAnimation.createRotated("destruction/fire4", 3),
            fireAnimation.createRotated("destruction/fire4", 4),
            fireAnimation.createRotated("destruction/fire5", 5),
            fireAnimation.createRotated("destruction/fire6", 6),
            fireAnimation.createRotated("destruction/fire7", 7),
            fireAnimation.createRotated("destruction/fire8", 8),
            fireAnimation.createRotated("destruction/fire9", 9),
            fireAnimation.createRotated("destruction/fire10", 10),
            fireAnimation.createRotated("destruction/fire11", 11),
            fireAnimation.createRotated("destruction/fire12", 12),
            fireAnimation.createRotated("destruction/fire13", 13),
            fireAnimation.createRotated("destruction/fire14", 14),
            fireAnimation.createRotated("destruction/fire15", 15)
    ), 1);
    
    public enum Variant implements StructureVariant
    {
        V1, V2, V3, V4, V5, V6, V7, V8,
        V9, V10, V11, V12, V13, V14, V15, V16;
        
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

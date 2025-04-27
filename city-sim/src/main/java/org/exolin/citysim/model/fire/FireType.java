package org.exolin.citysim.model.fire;

import java.util.ArrayList;
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
    private static List<Animation> create()
    {
        int len = 16;
        Animation fireAnimation = createAnimation("destruction/fire", len, 500);
        
        List<Animation> images = new ArrayList<>();
        for(int i=0;i<len;++i)
        {
            if(i == 0)
                images.add(fireAnimation);
            else
                images.add(fireAnimation.createRotated("destruction/fire"+(i+1), i));
        }
        
        return images;
    }
    
    public static final FireType fire = new FireType("fire", create(), 1);
    
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

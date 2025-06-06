package org.exolin.citysim.model.fire;

import java.util.ArrayList;
import java.util.List;
import org.exolin.citysim.model.Animation;
import static org.exolin.citysim.model.Animation.createAnimation;
import org.exolin.citysim.model.StructureSize;
import static org.exolin.citysim.model.StructureSize._1;
import org.exolin.citysim.model.StructureType;

/**
 *
 * @author Thomas
 */
public class FireType extends StructureType<Fire, FireVariant, FireParameters>
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
    
    public static final FireType fire = new FireType("fire", create(), _1);
    
    public FireType(String name, List<Animation> animation, StructureSize size)
    {
        super(name, animation, size);
    }

    @Override
    public int getBuildingCost(FireVariant variant)
    {
        return NO_COST;
    }

    @Override
    public Fire createBuilding(int x, int y, FireVariant variant, FireParameters data)
    {
        return new Fire(this, x, y, variant, data);
    }

    @Override
    public FireVariant getVariantForDefaultImage()
    {
        return FireVariant.V1;
    }
}

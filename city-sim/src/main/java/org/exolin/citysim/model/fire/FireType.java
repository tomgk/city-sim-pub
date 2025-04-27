package org.exolin.citysim.model.fire;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.IntStream;
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
    
    public static class Variant implements StructureVariant
    {
        private final int version;

        public Variant(int version)
        {
            this.version = version;
        }
        
        private static final List<Variant> VALUES = IntStream.range(1, 17)
                .mapToObj(i -> new Variant(i))
                .toList();
        
        public static final Variant V1 = VALUES.getFirst();
        
        public static int getVariantCount()
        {
            return VALUES.size();
        }
        
        public static Variant random()
        {
            return VALUES.get((int)(Math.random()*VALUES.size()));
        }

        @Override
        public int index()
        {
            return version-1;
        }

        @Override
        public String name()
        {
            return "V"+version;
        }
        
        public static Variant valueOf(String name)
        {
            for(Variant v : VALUES)
            {
                if(v.name().equals(name))
                    return v;
            }
            
            throw new NoSuchElementException();
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

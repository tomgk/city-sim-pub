package org.exolin.citysim;

import java.util.Set;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import static org.exolin.citysim.storage.StructureData.DEFAULT_NAME;

/**
 *
 * @author Thomas
 */
public class InfoResources
{
    static
    {
        StructureTypes.init();
    }
    
    public static void resourceInfo(Printer out)
    {
        StructureType.types().forEach((StructureType s) -> {
            resourceInfo(out, s);
        });
    }
    
    private static String toString(Animation a)
    {
        if(a.isUnanimated())
            return a.getName();
        else
        {
            return a.getName()+", "+a.getImageCount()+" f, "+a.getAnimationSpeed()+" ms/f";
        }
    }
    
    public static <B, E extends StructureVariant, D extends StructureParameters> void resourceInfo(
            Printer out, StructureType<B, E, D> s)
    {
        out.println("==== "+s.getName()+" =====");
        
        Set<E> variants = s.getVariants();
        if(variants.size() == 1)
            out.println("Image: "+toString(s.getImage(s.getVariantForDefaultImage())));
        else
        {
            String d = s.getVariantForDefaultImage().name();
            if(!d.equals(DEFAULT_NAME))
                out.println("DefaultImage: "+d);
            
            out.println("Image:");
            variants.forEach(v -> {
                Animation a = s.getImage(v);
                out.println(" - "+v.name()+": "+toString(a));
            });
        }
    }
    
    public static void main(String[] args)
    {
        resourceInfo(System.out::println);
    }
}

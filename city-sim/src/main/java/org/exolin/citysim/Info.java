package org.exolin.citysim;

import java.util.Objects;
import java.util.Set;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class Info
{
    static
    {
        StructureTypes.init();
    }
    
    public static void main(String[] args)
    {
        StructureType.types().forEach((StructureType s) -> {
            System.out.println("==== "+s.getName()+" =====");
            
            Set<? extends StructureVariant> variants;
            try{
                variants = s.getVariants();
                Objects.requireNonNull(variants);
            }catch(UnsupportedOperationException e){
                variants = null;
            }
            if(variants != null)
            {
                if(variants.size() == 1)
                    System.out.println("Cost: "+s.getBuildingCost(variants.iterator().next()));
                else
                {
                    System.out.println("Cost:");
                    variants.forEach(v -> {
                        System.out.println("  "+v.name()+": "+s.getBuildingCost(v)); 
                    });
                }
            }
            else
            {
                System.out.println("Cost:");
                
            }
        });
    }
}

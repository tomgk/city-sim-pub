package org.exolin.citysim;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
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
        System.out.println("--- C L A S S E S ---");
        
        StructureType.types()
                .stream()
                .map(t -> t.getClass())
                .distinct()
                .forEach(t -> {
                    System.out.println("==== "+t.getSimpleName()+" =====");
                    Set<? extends StructureVariant> variants = StructureVariant.getValues((Class)t);
                    System.out.println("Variants: "+variants.stream()
                            .map(v -> v.name())
                            .collect(Collectors.joining(", "))
                    );
                });
        
        System.out.println("--- T Y P E S ---");
        
        StructureType.types().forEach((StructureType s) -> {
            System.out.println("==== "+s.getName()+" =====");
            System.out.println("Type: "+s.getClass().getSimpleName());
            
            Set<? extends StructureVariant> variants;
            try{
                variants = s.getVariants();
                Objects.requireNonNull(variants);
            }catch(UnsupportedOperationException e){
                variants = null;
            }
            if(variants != null)
            {
                Set<Integer> diffCosts = variants.stream()
                        .map(v -> s.getBuildingCost(v))
                        .collect(Collectors.toSet());
                
                if(diffCosts.size() == 1)
                {
                    System.out.println("Cost: "+diffCosts.iterator().next());
                }
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
                System.out.println("Cost: unknown");
            }
            
            s.customKeys().forEach(key -> {
                System.out.println(key+": "+s.getCustom((String)key, Object.class));
            });
        });
    }
}

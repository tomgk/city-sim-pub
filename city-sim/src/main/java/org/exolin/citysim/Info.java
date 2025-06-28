package org.exolin.citysim;

import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.CustomKey;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;

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
                    Class<? extends StructureVariant> vc = StructureType.getStructureVariantClass(t);
                    Set<? extends StructureVariant> variants = StructureVariant.getValues(vc);
                    System.out.println("Variants: "+variants.stream()
                            .sorted(Comparator.comparing(StructureVariant::index))
                            .map(v -> v.name())
                            .collect(Collectors.joining(", "))
                    );
                });
        
        System.out.println("--- T Y P E S ---");
        
        StructureType.types().forEach((StructureType s) -> {
            System.out.println("==== "+s.getName()+" =====");
            System.out.println("Type: "+s.getClass().getSimpleName());
            
            Set<? extends StructureVariant> variants = s.getVariants();
            Objects.requireNonNull(variants);

            Set<Integer> diffCosts = variants.stream()
                    .map(v -> s.getBuildingCost(v))
                    .collect(Collectors.toSet());

            if(diffCosts.size() == 1)
            {
                System.out.println("Cost: "+formatCost(diffCosts.iterator().next()));
            }
            else
            {
                System.out.println("Cost:");
                variants.forEach(v -> {
                    System.out.println("  "+v.name()+": "+formatCost(s.getBuildingCost(v))); 
                });
            }
            
            if(s instanceof BuildingType bt)
                System.out.println("Zone: "+bt.getZoneType().getName());
            
            s.customKeys().forEach(key -> {
                CustomKey k = (CustomKey)key;
                Object v = s.getCustom(k, Object.class);
                System.out.println(k.getName()+": "+k.formatValue(v));
            });
        });
    }

    private static String formatCost(int cost)
    {
        return cost != 0 ? Integer.toString(cost) : "-";
    }
}

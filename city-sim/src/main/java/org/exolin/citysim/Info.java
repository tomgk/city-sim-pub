package org.exolin.citysim;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.CustomKey;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.plant.PlantType;

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
    
    interface Printer
    {
        void println(String out);
    }
    
    public static void classInfo(Printer out)
    {
        out.println("--- C L A S S E S ---");
        
        StructureType.types()
                .stream()
                .map(t -> t.getClass())
                .distinct()
                .forEach(t -> {
                    out.println("==== "+t.getSimpleName()+" =====");
                    Class<? extends StructureVariant> vc = StructureType.getStructureVariantClass(t);
                    Set<? extends StructureVariant> variants = StructureVariant.getValues(vc);
                    
                    boolean multiType = variants.stream()
                            .map(StructureVariant::getClass)
                            .distinct()
                            .count() > 1;
                    
                    out.println("Variants:"+(multiType ? "\n  " : " ")+variants.stream()
                            .sorted(Comparator.comparing(StructureVariant::index))
                            .map(v -> (multiType ? v.getClass().getSimpleName()+"." : "")+v.name())
                            .collect(Collectors.joining(!multiType ? ", " : "\n  "))
                    );
                });
    }
    
    public static void typeInfo(Printer out)
    {
        out.println("--- T Y P E S ---");
        
        StructureType.types().forEach((StructureType s) -> {
            out.println("==== "+s.getName()+" =====");
            out.println("Type: "+s.getClass().getSimpleName());
            
            Set<? extends StructureVariant> variants = s.getVariants();
            Objects.requireNonNull(variants);

            Set<Integer> diffCosts = variants.stream()
                    .map(v -> s.getBuildingCost(v))
                    .collect(Collectors.toSet());

            if(diffCosts.size() == 1)
            {
                out.println("Cost: "+formatCost(diffCosts.iterator().next()));
            }
            else
            {
                out.println("Cost:");
                variants.forEach(v -> {
                    out.println("  "+v.name()+": "+formatCost(s.getBuildingCost(v))); 
                });
            }
            
            if(s instanceof BuildingType bt)
                out.println("Zone: "+bt.getZoneType().getName());
            else if(s instanceof CrossConnectionType cct)
            {
                out.println("X-Type: "+cct.getXType().getName());
                out.println("Y-Type: "+cct.getYType().getName());
            }
            else if(s instanceof PlantType tt)
            {
                out.println("Count: "+tt.getCount());
                out.println("Type: "+tt.getType());
                out.println("IsAlive: "+tt.isAlive());
            }
            
            s.customKeys().forEach(key -> {
                CustomKey k = (CustomKey)key;
                Object v = s.getCustom(k, Object.class);
                out.println(k.getName()+": "+k.formatValue(v));
            });
        });
    }
    
    public static void main(String[] args)
    {
        Printer out = System.out::println;
        classInfo(out);
        typeInfo(out);
    }

    private static String formatCost(int cost)
    {
        return cost != 0 ? Integer.toString(cost) : "-";
    }
}

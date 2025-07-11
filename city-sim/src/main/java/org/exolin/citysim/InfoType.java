package org.exolin.citysim;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import static org.exolin.citysim.Info.formatCost;
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
public class InfoType
{
    static
    {
        StructureTypes.init();
    }
    
    public static void typeInfo(Printer out)
    {
        StructureType.types().forEach((StructureType s) -> {
            typeInfo(out, s);
        });
    }
    
    public static void typeInfo(Printer out, StructureType s)
    {
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
        
        out.println("Size: "+s.getSize().getSizeString());

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
    }
}

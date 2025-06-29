package org.exolin.citysim;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import static org.exolin.citysim.Info.formatCost;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.Animation;
import org.exolin.citysim.model.CustomKey;
import org.exolin.citysim.model.StructureParameters;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.plant.PlantType;

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
    
    public static <B, E extends StructureVariant, D extends StructureParameters> void resourceInfo(Printer out, StructureType<B, E, D> s)
    {
        out.println("==== "+s.getName()+" =====");
        s.getVariants().forEach(v -> {
            Animation a = s.getImage(v);
            System.out.println(v.name()+":"+a.getName());
        });
    }
    
    public static void main(String[] args)
    {
        resourceInfo(System.out::println);
    }
}

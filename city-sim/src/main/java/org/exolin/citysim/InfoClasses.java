package org.exolin.citysim;

import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;

/**
 *
 * @author Thomas
 */
public class InfoClasses
{
    static
    {
        StructureTypes.init();
    }
    
    public static Stream<Class> getTypeClasses()
    {
        return StructureType.types()
                .stream()
                .map(t -> (Class)t.getClass())
                .distinct()
                .sorted(Comparator.comparing(Class::getSimpleName));
    }
    
    public static void classInfo(Printer out)
    {
        getTypeClasses().forEach(t -> {
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
}

package org.exolin.citysim;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.StructureType;
import org.exolin.citysim.model.StructureVariant;
import org.exolin.citysim.model.building.BuildingType;
import static org.exolin.citysim.storage.StructureData.DEFAULT_NAME;

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
                .sorted(order());
    }
    
    private static Comparator<Class> order()
    {
        Comparator<Class> w = Comparator.comparing(InfoClasses::getSuperClassName);
        Comparator<Class> x = Comparator.comparing(Class::getSimpleName);
        return x;
    }
    
    private static String getSuperClassName(Class<?> c)
    {
        Class<?> superClass = c.getSuperclass();
        if(superClass.equals(StructureType.class))
            return "";
        return superClass.getSimpleName();
    }
    
    public static void classInfo(Printer out)
    {
        getTypeClasses().forEach(t -> {
                    classInfo(out, t);
                });
    }
    
    private static void classInfo(Printer out, Class t)
    {
        out.println("==== "+t.getSimpleName()+" =====");
        Class<? extends StructureVariant> vc = StructureType.getStructureVariantClass(t);
        Set<? extends StructureVariant> variants = StructureVariant.getValues(vc);
        
        Class superclass = t.getSuperclass();
        if(!superclass.equals(StructureType.class))
            out.println("Super: "+superclass.getSimpleName());
        
        if(variants.size() == 1 && variants.iterator().next().name().equals(DEFAULT_NAME))
        {
            //TODO: better text
            out.println("No variants");
            return;
        }

        boolean multiType = variants.stream()
                .map(StructureVariant::getClass)
                .distinct()
                .count() > 1;

        boolean hasInfo = variants.stream()
                .map(StructureVariant::getInfo)
                .map(Optional::isPresent)
                .reduce(false, (a, b) -> a||b);

        boolean multiLine = multiType || hasInfo;

        out.println("Variants:"+(multiLine ? "\n  " : " ")+variants.stream()
                .sorted(Comparator.comparing(StructureVariant::index))
                .map(v -> toString(v, multiType))
                .collect(Collectors.joining(!multiLine ? ", " : "\n  "))
        );
    }
    
    private static String toString(StructureVariant v, boolean multiType)
    {
        StringBuilder sb = new StringBuilder();
        if(multiType)
            sb.append(v.getClass().getSimpleName()).append(".");
        
        sb.append(v.name());
        
        var info = v.getInfo();
        if(info.isPresent())
            sb.append(" (").append(info.get()).append(")");
        
        return sb.toString();
    }
}

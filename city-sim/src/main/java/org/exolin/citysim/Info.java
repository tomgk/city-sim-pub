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
public class Info
{
    static
    {
        StructureTypes.init();
    }
    
    public static void main(String[] args)
    {
        Printer out = System.out::println;
        
        out.println("--- C L A S S E S ---");
        InfoClasses.classInfo(out);
        
        out.println("--- T Y P E S ---");
        InfoType.typeInfo(out);
    }

    static String formatCost(int cost)
    {
        return cost != 0 ? Integer.toString(cost) : "-";
    }
}

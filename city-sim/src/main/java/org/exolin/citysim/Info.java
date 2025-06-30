package org.exolin.citysim;

import org.exolin.citysim.bt.StructureTypes;

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

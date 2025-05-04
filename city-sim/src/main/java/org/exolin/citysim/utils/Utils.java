package org.exolin.citysim.utils;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.exolin.citysim.ui.WorldView;

/**
 *
 * @author Thomas
 */
public class Utils
{
    public static String getFilenameWithoutExt(Path path)
    {
        String fn = path.getFileName().toString();
        int pos = fn.indexOf('.');
        return pos != -1 ? fn.substring(0, pos) : fn;
    }
    
    public static <T> List<T> rotate(List<T> list, int distance)
    {
        List<T> copy = new ArrayList<>(list);
        Collections.rotate(copy, distance);
        return copy;
    }

    public static <E extends Enum<E>> E getPrev(E[] values, int ordinal)
    {
        int num = ordinal-1;
        if(num == -1)
            num = values.length-1;
        
        return values[num];
    }

    public static <E extends Enum<E>> E getNext(E[] values, int ordinal)
    {
        int num = ordinal+1;
        if(num == values.length)
            num = 0;
        
        return values[num];
    }
}

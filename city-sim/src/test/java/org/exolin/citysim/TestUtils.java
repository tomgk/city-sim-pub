package org.exolin.citysim;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.fail;

/**
 *
 * @author Thomas
 */
public class TestUtils
{
    public static void assertEqualSet(Set expected, Set actual)
    {
        if(expected.equals(actual))
            return;
        
        //expected without actual = what is missing
        Set missing = new HashSet(expected);
        missing.removeAll(actual);
        
        //actal without expected = tooMuch
        Set tooMuch = new HashSet(actual);
        tooMuch.removeAll(expected);
        
        List<String> msg = new ArrayList<>();
        if(!missing.isEmpty())
            msg.add("missing: "+missing);
        if(!tooMuch.isEmpty())
            msg.add("too much: "+tooMuch);
        
        fail(String.join("\n", msg));
    }
}

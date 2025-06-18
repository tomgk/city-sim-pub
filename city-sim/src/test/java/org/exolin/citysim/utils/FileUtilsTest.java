package org.exolin.citysim.utils;

import java.nio.file.Paths;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class FileUtilsTest
{
    @Test
    public void testGetFilenameWithoutExt_NoExtension()
    {
        assertEquals("xx", FileUtils.getFilenameWithoutExt(Paths.get("xx")));
    }
    
    @Test
    public void testGetFilenameWithoutExt_SingleExtension()
    {
        assertEquals("xx", FileUtils.getFilenameWithoutExt(Paths.get("xx.y")));
    }
    
    @Test
    public void testGetFilenameWithoutExt_DoubleExtension()
    {
        assertEquals("xx.y", FileUtils.getFilenameWithoutExt(Paths.get("xx.y.z")));
    }
}

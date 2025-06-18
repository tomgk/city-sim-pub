package org.exolin.citysim.utils;

import java.nio.file.Path;

/**
 *
 * @author Thomas
 */
public class FileUtils
{
    public static String getFilenameWithoutExt(Path path)
    {
        String fn = path.getFileName().toString();
        int pos = fn.lastIndexOf('.');
        return pos != -1 ? fn.substring(0, pos) : fn;
    }
}

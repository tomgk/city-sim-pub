package org.exolin.citysim.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import org.exolin.citysim.Building;
import org.exolin.citysim.World;

/**
 *
 * @author Thomas
 */
public class WorldStorage
{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void serialize(Building b, OutputStream out) throws IOException
    {
        objectMapper.writeValue(out, BuildingData.create(b));
    }
    
    public static void serialize(World w, OutputStream out) throws IOException
    {
        objectMapper.writeValue(out, new WorldData(w));
    }
}

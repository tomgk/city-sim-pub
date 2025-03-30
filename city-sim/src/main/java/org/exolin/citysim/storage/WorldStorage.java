package org.exolin.citysim.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.InputStream;
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
    
    public static void deserialize(InputStream in, World w) throws IOException
    {
        BuildingData bd = objectMapper.readValue(in, BuildingData.class);
        bd.createBuilding(w);
    }
    
    public static void serialize(World w, OutputStream out) throws IOException
    {
        objectMapper.writeValue(out, new WorldData(w));
    }
    
    public World deserialize(InputStream in) throws IOException
    {
        WorldData worldData = objectMapper.readValue(in, WorldData.class);
        return worldData.createWorld();
    }
}

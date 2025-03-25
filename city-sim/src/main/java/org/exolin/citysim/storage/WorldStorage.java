package org.exolin.citysim.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import org.exolin.citysim.ActualBuilding;
import org.exolin.citysim.Building;
import org.exolin.citysim.Street;

/**
 *
 * @author Thomas
 */
public class WorldStorage
{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void serialize(Building b, OutputStream out) throws IOException
    {
        Class<?> clazz = b.getClass();
        
        if(clazz == ActualBuilding.class)
            objectMapper.writeValue(out, new ActualBuildingData((ActualBuilding)b));
        else if(clazz == Street.class)
            objectMapper.writeValue(out, new StreetData((Street)b));
        else
            throw new UnsupportedOperationException(clazz.getName());
    }
}

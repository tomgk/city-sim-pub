package org.exolin.citysim.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.io.OutputStream;
import org.exolin.citysim.ActualBuilding;

/**
 *
 * @author Thomas
 */
public class WorldStorage
{
    private static final ObjectMapper objectMapper = new ObjectMapper();
    
    public static void serialize(ActualBuilding b, OutputStream out) throws IOException
    {
        objectMapper.writeValue(out, new ActualBuildingData(b));
    }
}

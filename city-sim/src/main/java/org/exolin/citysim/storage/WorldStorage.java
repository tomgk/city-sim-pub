package org.exolin.citysim.storage;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import org.exolin.citysim.bt.StructureTypes;
import org.exolin.citysim.model.Structure;
import org.exolin.citysim.model.World;

/**
 *
 * @author Thomas
 */
public class WorldStorage
{
    static
    {
        //WorldStorage loads types by name and not variable reference,
        //so it's possible that the types aren't loaded when WorldStorage is used
        //load manually here for that reason
        StructureTypes.init();
    }
    
    private static final ObjectMapper objectMapper = new ObjectMapper();
    static
    {
        objectMapper.registerModule(new Jdk8Module());
    }
    
    public static void serialize(Structure<?, ?, ?, ?> b, OutputStream out) throws IOException
    {
        objectMapper.writeValue(out, StructureData.create(b));
    }
    
    public static void deserialize(InputStream in, World w) throws IOException
    {
        StructureData bd = objectMapper.readValue(in, StructureData.class);
        bd.createBuilding(w);
    }
    
    public static void serialize(World w, OutputStream out) throws IOException
    {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(out, new WorldData(w));
    }
    
    public static World deserialize(String name, InputStream in) throws IOException
    {
        WorldData worldData = objectMapper.readValue(in, WorldData.class);
        return worldData.createWorld(name);
    }

    public static void save(World world, Path file) throws IOException
    {
        Path temp = file.resolveSibling(file.getFileName()+".tmp");
        
        try(OutputStream out = Files.newOutputStream(temp))
        {
            WorldStorage.serialize(world, out);
        }catch(IOException e){
            try{
                Files.delete(temp);
            }catch(IOException e2){
                e.addSuppressed(e2);
            }
            throw e;
        }
        
        Files.move(temp, file, StandardCopyOption.REPLACE_EXISTING);
    }
}

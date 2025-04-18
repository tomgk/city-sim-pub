package org.exolin.citysim.storage;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import org.exolin.citysim.model.StructureType;

/**
 *
 * @author Thomas
 */
public class BuildingDataTypeIdResolver implements TypeIdResolver
{
    private JavaType superType;

    @Override
    public void init(JavaType baseType) {
        superType = baseType;
    }

    @Override
    public String idFromValue(Object value) {
        return null;
    }

    @Override
    public String idFromValueAndType(Object value, Class<?> suggestedType) {
        return null;
    }

    @Override
    public JsonTypeInfo.Id getMechanism() {
        return null;
    }

    @Override
    public JavaType typeFromId(DatabindContext context, String id) throws IOException
    {
        Class<?> buildingTypeClass = StructureType.getByName(id).getClass();
        
        ParameterizedType superClass = (ParameterizedType)buildingTypeClass.getGenericSuperclass();
        Class<?> buildingClass = (Class)superClass.getActualTypeArguments()[0];
        
        return context.constructSpecializedType(superType, BuildingData.getBuildingDataClass(buildingClass));
    }

    @Override
    public String idFromBaseType()
    {
        return "baseBuilding";
    }

    @Override
    public String getDescForKnownTypeIds()
    {
        return "";
    }
}

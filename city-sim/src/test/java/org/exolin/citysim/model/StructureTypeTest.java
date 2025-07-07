package org.exolin.citysim.model;

import org.exolin.citysim.bt.Zones;
import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.building.vacant.VacantType;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.connection.regular.ConnectionVariant;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.fire.FireType;
import org.exolin.citysim.model.fire.FireVariant;
import org.exolin.citysim.model.plant.PlantType;
import org.exolin.citysim.model.plant.PlantVariant;
import org.exolin.citysim.model.zone.ZoneType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class StructureTypeTest
{
    @Test
    public void testTransformName_Simple()
    {
        assertEquals("water_1", StructureType.transformName("water_1"));
    }
    
    @Test
    public void testTransformName_SubDir()
    {
        assertEquals("water_1", StructureType.transformName("water/1"));
    }
    
    @Test
    public void testTransformName_SubDir_Duplicate()
    {
        assertEquals("water_1", StructureType.transformName("water/water_1"));
    }
    
    @Test
    public void testGetStructureVariantClass_BuildingType()
    {
        assertEquals(BuildingType.Variant.class, StructureType.getStructureVariantClass(BuildingType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_ConnectionType()
    {
        try{
            StructureType.getStructureVariantClass(ConnectionType.class);
            fail();
        }catch(IllegalArgumentException e){
            assertEquals("Could not determine "+StructureVariant.class.getName()+" for "+ConnectionType.class.getName(), e.getMessage());
        }
    }
    
    @Test
    public void testGetStructureVariantClass_CrossConnectionType()
    {
        assertEquals(SingleVariant.class, StructureType.getStructureVariantClass(CrossConnectionType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_SelfConnectionType()
    {
        assertEquals(ConnectionVariant.class, StructureType.getStructureVariantClass(SelfConnectionType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_VacantType()
    {
        assertEquals(SingleVariant.class, StructureType.getStructureVariantClass(VacantType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_FireType()
    {
        assertEquals(FireVariant.class, StructureType.getStructureVariantClass(FireType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_PlantType()
    {
        assertEquals(PlantVariant.class, StructureType.getStructureVariantClass(PlantType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_ZoneType()
    {
        assertEquals(SingleVariant.class, StructureType.getStructureVariantClass(ZoneType.class));
    }
    
    @Test
    public void test()
    {
        Class a = StructureType.getStructureVariantClass(Zones.business.getClass());
        assertEquals(SingleVariant.class, a);
    }
}

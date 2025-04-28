package org.exolin.citysim.model;

import org.exolin.citysim.model.building.BuildingType;
import org.exolin.citysim.model.connection.ConnectionType;
import org.exolin.citysim.model.connection.cross.CrossConnectionType;
import org.exolin.citysim.model.connection.regular.ConnectionVariant;
import org.exolin.citysim.model.connection.regular.SelfConnectionType;
import org.exolin.citysim.model.destroyed.DestroyedType;
import org.exolin.citysim.model.fire.FireType;
import org.exolin.citysim.model.fire.FireVariant;
import org.exolin.citysim.model.tree.TreeType;
import org.exolin.citysim.model.tree.TreeVariant;
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
        assertEquals(CrossConnectionType.Variant.class, StructureType.getStructureVariantClass(CrossConnectionType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_SelfConnectionType()
    {
        assertEquals(ConnectionVariant.class, StructureType.getStructureVariantClass(SelfConnectionType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_DestroyedType()
    {
        assertEquals(DestroyedType.Variant.class, StructureType.getStructureVariantClass(DestroyedType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_FireType()
    {
        assertEquals(FireVariant.class, StructureType.getStructureVariantClass(FireType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_TreeType()
    {
        assertEquals(TreeVariant.class, StructureType.getStructureVariantClass(TreeType.class));
    }
    
    @Test
    public void testGetStructureVariantClass_ZoneType()
    {
        assertEquals(ZoneType.Variant.class, StructureType.getStructureVariantClass(ZoneType.class));
    }
}

package org.exolin.citysim.model;

import java.util.List;
import org.exolin.citysim.Constants;
import static org.exolin.citysim.Constants.DEFAULT_NONANIMATION_SPEED;
import static org.exolin.citysim.model.Animation.createAnimation;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Thomas
 */
public class AnimationTest
{
    @Test
    public void createAnimated()
    {
        Animation a = createAnimation("plants/gas_plant", 8);
        assertEquals(List.of(
                "plants/gas_plant",
                "plants/gas_plant_1",
                "plants/gas_plant_2",
                "plants/gas_plant_3",
                "plants/gas_plant_4",
                "plants/gas_plant_5",
                "plants/gas_plant_6",
                "plants/gas_plant_7"
        ), a.getFileNames());
        
        assertEquals(Constants.DEFAULT_ANIMATION_SPEED, a.getAnimationSpeed());
        assertEquals("plants/gas_plant", a.getName());
        assertEquals(8, a.getImageCount());
        
        try{
            a.getUnaminatedFileName();
            fail();
        }catch(IllegalStateException e){
            assertEquals("not an unanimation", e.getMessage());
        }
    }
    
    @Test
    public void createUnnimated()
    {
        Animation a = Animation.createUnanimated("plants/gas_plant");
        assertEquals(List.of("plants/gas_plant"), a.getFileNames());
        assertEquals("plants/gas_plant", a.getUnaminatedFileName());
        assertEquals(DEFAULT_NONANIMATION_SPEED, a.getAnimationSpeed());
        assertEquals("plants/gas_plant", a.getName());
        assertEquals(1, a.getImageCount());
    }
}

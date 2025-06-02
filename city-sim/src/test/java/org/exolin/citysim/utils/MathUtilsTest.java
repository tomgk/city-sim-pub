package org.exolin.citysim.utils;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Thomas
 */
public class MathUtilsTest
{
    private final static Stream<Arguments> lcmTestValues()
    {
        return Stream.of(
                //one
                Arguments.of(List.of(3), 3),
                Arguments.of(List.of(7), 7),
                Arguments.of(List.of(10), 10),
                
                //two:
                Arguments.of(List.of(2, 5), 10),
                Arguments.of(List.of(15, 35), 105),
                
                //three
                Arguments.of(List.of(15, 35, 2), 210)
        );
    }
    
    @Test
    public void testLCM_Empty()
    {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            MathUtils.lcm(IntStream.empty());
        });
        assertEquals("No values", e.getMessage());
    }
    
    @ParameterizedTest
    @MethodSource("lcmTestValues")
    public void testLCM(List<Integer> nums, int expected)
    {
        assertEquals(expected, MathUtils.lcm(nums.stream().mapToInt(Integer::intValue)));
    }
}

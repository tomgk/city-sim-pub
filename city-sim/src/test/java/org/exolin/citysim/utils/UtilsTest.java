package org.exolin.citysim.utils;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Thomas
 */
public class UtilsTest
{
    @ParameterizedTest
    @MethodSource("probabilityForTicksTestValues")
    public void testGetProbabilityForTicks(double probability, int ticks, double expected)
    {
        assertEquals(expected, Utils.getProbabilityForTicks(probability, ticks), 0.0001);
    }
    
    @SuppressWarnings("unused")
    private static Stream<Arguments> probabilityForTicksTestValues()
    {
        return Stream.of(
                Arguments.of(0.1,   0, 0),
                Arguments.of(0.1,   1, 0.1),
                Arguments.of(0.1,   2, 0.19),
                Arguments.of(0.1,   3, 0.271),
                Arguments.of(0.1,  10, 0.6513215599),
                Arguments.of(0.1, 100, 0.9999734386),
                
                Arguments.of(0.001,    0, 0),
                Arguments.of(0.001,    1, 0.001),
                Arguments.of(0.001,    2, 0.001999),
                Arguments.of(0.001,   10, 0.00995511979),
                Arguments.of(0.001,  100, 0.09520785288),
                Arguments.of(0.001, 1000, 0.63230457522),
                
                Arguments.of(0.1, 0, 0)
        );
    }
    
    @Test
    @SuppressWarnings("ThrowableResultIgnored")
    public void testGetProbabilityForTicks_Negative()
    {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Utils.getProbabilityForTicks(0.1, -1));
    }
}

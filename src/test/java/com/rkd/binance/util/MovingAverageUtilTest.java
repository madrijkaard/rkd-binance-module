package com.rkd.binance.util;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MovingAverageUtilTest {

    @Test
    @DisplayName("Tests the amplitude between two random, non-zero values")
    public void calculateAmplitude() {

        double[] prices = TestUtil.generateTwoRandomDoubles();

        var result = MovingAverageUtil.calculateAmplitude(prices[0], prices[1]);

        assertTrue(result > 0);
    }

    @Test
    @DisplayName("Tests the amplitude between two random values, one of which is zero")
    public void calculateAmplitudeWithLmaZero() {

        double[] prices = TestUtil.generateTwoRandomDoubles();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> MovingAverageUtil.calculateAmplitude(prices[0], 0));

        assertEquals("Illegal argument: sma or lma equal to zero", exception.getMessage());
    }

    @Test
    @DisplayName("Tests the amplitude between two random values, one of which is zero")
    public void calculateAmplitudeWithSmaZero() {

        double[] prices = TestUtil.generateTwoRandomDoubles();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> MovingAverageUtil.calculateAmplitude(0, prices[1]));

        assertEquals("Illegal argument: sma or lma equal to zero", exception.getMessage());
    }

    @Test
    @DisplayName("Tests the amplitude between two random values, one of which is negative")
    public void calculateAmplitudeWithSmaNegative() {

        double[] prices = TestUtil.generateTwoRandomDoubles();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> MovingAverageUtil.calculateAmplitude(-1, prices[1]));

        assertEquals("Illegal argument: sma or lma less than zero", exception.getMessage());
    }

    @Test
    @DisplayName("Tests the amplitude between two random values, one of which is negative")
    public void calculateAmplitudeWithLmaNegative() {

        double[] prices = TestUtil.generateTwoRandomDoubles();

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> MovingAverageUtil.calculateAmplitude(prices[0], -1));

        assertEquals("Illegal argument: sma or lma less than zero", exception.getMessage());
    }
}

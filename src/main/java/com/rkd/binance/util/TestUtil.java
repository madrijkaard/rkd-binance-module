package com.rkd.binance.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.Random;

public class TestUtil {

    public static double[] generateTwoRandomDoubles() {

        Random random = new Random();

        double lowerBound = 100000.0;
        double upperBound = 200000.0;

        double firstNumber = lowerBound + random.nextDouble() * (upperBound - lowerBound);
        double secondNumber = lowerBound + random.nextDouble() * (upperBound - lowerBound);

        double[] randomNumbers = new double[2];

        if (random.nextBoolean()) {
            randomNumbers[0] = firstNumber;
            randomNumbers[1] = secondNumber;
        } else {
            randomNumbers[0] = secondNumber;
            randomNumbers[1] = firstNumber;
        }

        return randomNumbers;
    }

    public static float[] generateTwoRandomFloats() {

        Random random = new Random();

        float minimumRange;
        float maximumRange;

        float value1 = 30 + random.nextFloat() * 60;
        float value2 = 30 + random.nextFloat() * 60;

        if (value1 < value2) {
            minimumRange = value1;
            maximumRange = value2;
        } else {
            minimumRange = value2;
            maximumRange = value1;
        }

        return new float[]{minimumRange, maximumRange};
    }

    public static double generateRandomPrice() {
        Random random = new Random();
        double value = 10000 + (100000 - 10000) * random.nextDouble();
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double generateRandomMoney() {
        Random random = new Random();
        double value = 1000000 + (10000000 - 1000000) * random.nextDouble();
        BigDecimal bd = new BigDecimal(value).setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double generateRandomInteger() {
        return new Random().nextInt(10000, 100000);
    }
}

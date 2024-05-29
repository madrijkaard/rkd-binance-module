package com.rkd.binance.util;

import com.rkd.binance.type.VectorType;

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

    public static String getRandomVectorType() {
        VectorType[] values = VectorType.values();
        int index = new Random().nextInt(values.length);
        return values[index].name();
    }
}

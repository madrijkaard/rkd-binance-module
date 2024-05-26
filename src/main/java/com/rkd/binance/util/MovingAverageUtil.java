package com.rkd.binance.util;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

public class MovingAverageUtil {

    /**
     * Method responsible for calculating the amplitude between two prices.
     *
     * @param sma short moving average
     * @param lma long moving average
     * @return amplitude that will be presented as a percentage
     * @throws IllegalArgumentException Illegal argument: sma or lma equal to zero
     * @throws IllegalArgumentException Illegal argument: sma or lma less than zero
     * @throws IllegalArgumentException Illegal argument: sma bigger than lma
     */
    public static double calculateAmplitude(double sma, double lma) {

        if (sma == 0 || lma == 0) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT + "sma or lma equal to zero");
        }

        if (sma < 0 || lma < 0) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT + "sma or lma less than zero");
        }

        double min = Math.min(sma, lma);
        double max = Math.max(sma, lma);

        return ((max - min) / min) * 100;
    }
}

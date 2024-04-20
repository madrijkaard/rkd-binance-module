package com.rkd.binance.type;

import java.util.Arrays;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

/**
 * Enumerator responsible for defining chart periods.
 */
public enum IntervalType {

    ONE_DAY("1d");

    IntervalType(String interval) {
        this.interval = interval;
    }

    private String interval;

    public String getInterval() {
        return interval;
    }

    /**
     * Method responsible for validating the enumerator's input data.
     *
     * @param interval chart period
     * @return value found in enumerator
     */
    public static IntervalType of(String interval) {
        return Arrays.stream(IntervalType.values())
                .filter(intervalType -> intervalType.interval.equalsIgnoreCase(interval)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(interval.concat(ILLEGAL_ARGUMENT)));
    }
}

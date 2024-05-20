package com.rkd.binance.type;

import java.util.Arrays;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;
import static com.rkd.binance.definition.ExceptionDefinition.INTERVAL_IS_NOT_MAPPED;

/**
 * Enumerator responsible for defining chart periods.
 * @see <a href="https://binance-docs.github.io/apidocs/spot/en/#kline-candlestick-streams">Kline/Candlestick Streams</a>
 */
public enum IntervalType {
    ONE_HOUR("1h"),
    ONE_DAY("1d"),
    ONE_WEEK("1w");

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

    public static IntervalType getIntervalByTimestamp(long timestamp1, long timestamp2) {

        long differenceInMillis = Math.abs(timestamp2 - timestamp1);
        double differenceInHours = differenceInMillis / (1000.0 * 60 * 60);
        long roundedDifferenceInHours = Math.round(differenceInHours);

        if (roundedDifferenceInHours == 1) {
            return ONE_HOUR;
        } else if (roundedDifferenceInHours == 24) {
            return ONE_DAY;
        } else if (roundedDifferenceInHours == 168) {
            return ONE_WEEK;
        } else {
            throw new IllegalArgumentException(INTERVAL_IS_NOT_MAPPED);
        }
    }
}

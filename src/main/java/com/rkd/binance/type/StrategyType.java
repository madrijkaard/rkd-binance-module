package com.rkd.binance.type;

import java.util.Arrays;
import java.util.List;

/**
 * Class responsible for representing strategy types.
 */
public enum StrategyType {

    ONE_MA(Arrays.asList("1h", "4h", "1d"), new int[]{11}),
    TWO_MA(Arrays.asList("1d"), new int[]{7, 30});

    private List<String> intervals;
    private int[] ma;

    StrategyType(List<String> intervals, int[] ma) {
        this.intervals = intervals;
        this.ma = ma;
    }

    public List<String> getIntervals() {
        return intervals;
    }

    public int[] getMa() {
        return ma;
    }

    public static StrategyType of(String strategy) {
        return Arrays.stream(StrategyType.values())
                .filter(strategyType -> strategyType.name().equalsIgnoreCase(strategy))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}

package com.rkd.binance.type;

import java.util.Arrays;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

public enum LeverageType {
    X5(5),
    X10(10),
    X20(20);

    private int leverage;

    LeverageType(int leverage) {
        this.leverage = leverage;
    }

    public int getLeverage() {
        return leverage;
    }

    public static LeverageType of(int leverage) {
        return Arrays.stream(LeverageType.values())
                .filter(leverageType -> leverageType.getLeverage() == leverage).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("symbol")));
    }
}

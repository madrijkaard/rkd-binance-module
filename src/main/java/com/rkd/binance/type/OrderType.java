package com.rkd.binance.type;

import java.util.Arrays;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

public enum OrderType {

    MARKET,
    LIMIT;

    public static OrderType of(String order) {
        return Arrays.stream(OrderType.values())
                .filter(decisionType -> decisionType.name().equalsIgnoreCase(order)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("order")));
    }
}

package com.rkd.binance.type;

import java.util.Arrays;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

public enum PositionSideType {

    LONG,
    SHORT;

    public static PositionSideType of(String positionSide) {
        return Arrays.stream(PositionSideType.values())
                .filter(marginType -> marginType.name().equalsIgnoreCase(positionSide)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("position side")));
    }
}

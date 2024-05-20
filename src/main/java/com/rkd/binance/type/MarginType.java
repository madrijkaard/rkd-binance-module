package com.rkd.binance.type;

import java.util.Arrays;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

/**
 * Enumerator responsible for defining margin types
 *
 * ISOLATED:
 *
 * In this modality, the margin allocated to a position is isolated to that specific position.
 * If the position is liquidated, only the margin allocated to that position will be lost, protecting the remainder of the account balance.
 * The margin can be adjusted manually for each individual position.
 *
 * CROSSED:
 *
 * In this modality, all available margin in the futures account is shared among all open positions.
 * This means that in the event of liquidation, the maximum loss could include the entire futures account balance.
 * Uses available margin to avoid liquidations, sharing risk and reward among all open positions.
 */
public enum MarginType {
    ISOLATED,
    CROSSED;

    public static MarginType of(String margin) {
        return Arrays.stream(MarginType.values())
                .filter(marginType -> marginType.name().equalsIgnoreCase(margin)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("margin")));
    }
}

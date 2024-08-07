package com.rkd.binance.type;

import java.util.Arrays;
import java.util.Random;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

public enum DecisionType {

    BUY,
    CROSS_BUY,
    SELL,
    CROSS_SELL,
    WAIT;

    private static final Random RANDOM = new Random();

    public static DecisionType of(String decision) {
        return Arrays.stream(DecisionType.values())
                .filter(decisionType -> decisionType.name().equalsIgnoreCase(decision)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("decision")));
    }

    public static boolean isCrossBuy(DecisionType decisionType) {
        return decisionType != null && decisionType.equals(CROSS_BUY);
    }

    public static boolean isCrossSell(DecisionType decisionType) {
        return decisionType != null && decisionType.equals(CROSS_SELL);
    }

    public static DecisionType getRandomDecision() {
        DecisionType[] values = DecisionType.values();
        return values[RANDOM.nextInt(values.length)];
    }
}

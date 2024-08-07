package com.rkd.binance.type;

import java.util.Arrays;
import java.util.Random;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

public enum VectorType {

    UP,
    DOWN;

    private static final Random RANDOM = new Random();

    public static VectorType of(String vector) {
        return Arrays.stream(VectorType.values())
                .filter(vectorType -> vectorType.name().equalsIgnoreCase(vector)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("vector")));
    }

    public static boolean isUp(VectorType vectorType) {
        return vectorType.name().equalsIgnoreCase(UP.name());
    }

    public static boolean isDown(VectorType vectorType) {
        return vectorType.name().equalsIgnoreCase(DOWN.name());
    }

    public static VectorType getRandomVector() {
        VectorType[] values = VectorType.values();
        return values[RANDOM.nextInt(values.length)];
    }
}

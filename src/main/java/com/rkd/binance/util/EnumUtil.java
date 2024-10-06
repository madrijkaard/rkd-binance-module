package com.rkd.binance.util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class EnumUtil {

    public static <T extends Enum<T>> T of(Class<T> enumClass, String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(enumValue -> enumValue.name().equalsIgnoreCase(value))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid argument for " + enumClass.getSimpleName() + ": " + value));
    }

    public static <T extends Enum<T>> Optional<T> exist(Class<T> enumClass, String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .filter(enumValue -> enumValue.name().equalsIgnoreCase(value))
                .findFirst();
    }

    public static <T extends Enum<T>> boolean exists(Class<T> enumClass, String value) {
        return Arrays.stream(enumClass.getEnumConstants())
                .anyMatch(enumValue -> enumValue.name().equalsIgnoreCase(value));
    }

    public static <T extends Enum<T>> boolean exist(List<T> list, T value) {
        return list.stream().anyMatch(enumValue -> enumValue.equals(value));
    }
}

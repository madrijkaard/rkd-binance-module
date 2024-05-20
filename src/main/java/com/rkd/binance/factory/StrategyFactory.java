package com.rkd.binance.factory;

import com.rkd.binance.type.StrategyType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rkd.binance.type.StrategyType.of;

/**
 *
 */
@Configuration
public class StrategyFactory {

    @Value("#{${strategies}}")
    private HashMap<String, Boolean> strategies = new HashMap<>();

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public List<StrategyType> strategy() {
        return strategies.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(entry -> of(entry.getKey()))
                .collect(Collectors.toList());
    }
}
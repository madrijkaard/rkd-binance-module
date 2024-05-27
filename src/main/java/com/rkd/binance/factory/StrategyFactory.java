package com.rkd.binance.factory;

import com.rkd.binance.facade.StrategyFacade;
import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.StrategyType;
import org.springframework.beans.factory.annotation.Autowired;
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

@Configuration
public class StrategyFactory {

    @Value("#{${module.spot.strategy}}")
    private HashMap<String, Boolean> spotStrategy = new HashMap<>();
    @Value("#{${module.future.strategy}}")
    private HashMap<String, Boolean> futureStrategy = new HashMap<>();

    @Autowired
    private StrategyFacade strategyFacade;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public List<StrategyType> spotStrategy() {
        return spotStrategy.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(entry -> of(entry.getKey()))
                .collect(Collectors.toList());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public List<StrategyType> futureStrategy() {
        return futureStrategy.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(entry -> of(entry.getKey()))
                .collect(Collectors.toList());
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public StrategyFacade strategy() {
        return strategyFacade;
    }
}
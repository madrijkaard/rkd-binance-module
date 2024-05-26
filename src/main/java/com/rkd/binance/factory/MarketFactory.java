package com.rkd.binance.factory;

import com.rkd.binance.facade.MarketFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MarketFactory {

    @Value("${binance.market.minimum-stable-coin}")
    private double minimumStableCoin;
    @Value("${binance.market.minimum-fiat-coin}")
    private double minimumFiatCoin;

    @Autowired
    private MarketFacade marketFacade;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MarketFacade market() {
        return marketFacade;
    }

    public double getMinimumStableCoin() {
        return minimumStableCoin;
    }

    public double getMinimumFiatCoin() {
        return minimumFiatCoin;
    }
}

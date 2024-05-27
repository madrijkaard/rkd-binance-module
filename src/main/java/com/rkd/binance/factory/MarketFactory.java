package com.rkd.binance.factory;

import com.rkd.binance.facade.MarketFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class MarketFactory {

    @Autowired
    private MarketFacade marketFacade;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MarketFacade market() {
        return marketFacade;
    }
}

package com.rkd.binance.factory;

import com.rkd.binance.facade.MarketFacade;
import com.rkd.binance.type.CryptoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class MarketFactory {

    @Value("${module.spot.rank}")
    private int spotRank;
    @Value("${module.future.rank}")
    private int futureRank;

    @Autowired
    private MarketFacade marketFacade;

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public MarketFacade market() {
        return marketFacade;
    }

    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public List<CryptoType> rankSpot() {
        return spotRank == 0 ? new ArrayList<>() : marketFacade.rankMostRelevant(spotRank);
    }
}

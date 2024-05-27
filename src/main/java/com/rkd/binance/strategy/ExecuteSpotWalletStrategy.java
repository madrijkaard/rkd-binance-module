package com.rkd.binance.strategy;

import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.type.StrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 */
@Service
public class ExecuteSpotWalletStrategy {

    @Autowired
    private ExecuteTradeStrategy executeTradeStrategy;
    @Autowired
    private TradeSpotStrategy tradeSpotStrategy;

    /**
     *
     * @param spotWalletDto
     * @param minimumStableCoin
     * @param stableCoin
     * @param spotVector
     * @param spotMinimumRange
     * @param spotMaximumRange
     * @param strategyTypeList
     */
    public void initializeSpot(SpotWalletDto spotWalletDto, double minimumStableCoin, String stableCoin, String spotVector,
                               float spotMinimumRange, float spotMaximumRange, List<StrategyType> strategyTypeList) {

        spotWalletDto.cryptos().forEach(

                (cryptoType, money) -> {

                    if (money < minimumStableCoin) {
                        return;
                    }

                    var symbol = cryptoType.name() + stableCoin;

                    var decision = executeTradeStrategy.initialize(symbol, spotVector, spotMinimumRange, spotMaximumRange, strategyTypeList);
                });
    }
}

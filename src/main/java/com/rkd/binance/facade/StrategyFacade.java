package com.rkd.binance.facade;

import com.rkd.binance.strategy.TwoMaStrategy;
import com.rkd.binance.type.DecisionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Facade responsible for aggregating all the module's trading strategies.
 */
@Service
public class StrategyFacade {

    @Autowired
    private TwoMaStrategy twoMaStrategy;

    /**
     * Method responsible for executing the strategy using two moving averages.
     *
     * @param symbol    currency pair
     * @param intervals chart period
     * @param ma        number of candlesticks that will return
     * @return type of decision: buy, sell and wait
     */
    public DecisionType executeTwoMa(String symbol, String vector, float minimumRange, float maximumRange, List<String> intervals, int... ma) {
        return twoMaStrategy.execute(symbol, vector, minimumRange, maximumRange, intervals, ma);
    }
}

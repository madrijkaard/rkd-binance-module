package com.rkd.binance.strategy;

import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.StrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExecuteTradeStrategy {

    @Autowired
    private TwoMaStrategy twoMaStrategy;

    public DecisionType initialize(String symbol, String vector, float minimumRange, float maximumRange, List<StrategyType> strategyTypeList) {

        return strategyTypeList.stream().map(

                strategyType -> {

                    var intervals = strategyType.getIntervals();
                    var ma = strategyType.getMa();

                    switch (strategyType) {
                        case TWO_MA:
                            return twoMaStrategy.execute(symbol, vector, minimumRange, maximumRange, intervals, ma);
                        default:
                            throw new IllegalArgumentException();
                    }

                }).findFirst().orElseThrow(IllegalStateException::new);
    }
}

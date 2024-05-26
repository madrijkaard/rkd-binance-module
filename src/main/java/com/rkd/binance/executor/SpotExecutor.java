package com.rkd.binance.executor;

import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.type.DecisionType;
import org.springframework.stereotype.Component;

@Component
public class SpotExecutor {

    /*private DecisionType getDecision(String symbol) {

        return strategyFactory.spotStrategy().stream().map(

                strategyType -> {

                    var intervals = strategyType.getIntervals();
                    var ma = strategyType.getMa();

                    switch (strategyType) {
                        case TWO_MA:
                            return strategyFactory.strategy().executeTwoMa(symbol, intervals, ma);
                        default:
                            throw new IllegalArgumentException();
                    }

                }).findFirst().orElseThrow(IllegalStateException::new);
    }*/
}

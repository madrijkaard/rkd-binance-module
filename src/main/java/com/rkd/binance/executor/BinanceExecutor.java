package com.rkd.binance.executor;

import com.rkd.binance.factory.MarketFactory;
import com.rkd.binance.factory.StrategyFactory;
import com.rkd.binance.factory.WalletFactory;
import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.VectorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public abstract class BinanceExecutor {

    // properties
    @Value("${module.spot.status}")
    private boolean spotStatus;
    @Value("${module.future.status}")
    private boolean futureStatus;
    @Value("${module.spot.vector}")
    private String spotVector;
    @Value("${module.future.vector}")
    private String futureVector;
    @Value("${module.spot.minimum-range}")
    private float spotMinimumRange;
    @Value("${module.spot.maximum-range}")
    private float spotMaximumRange;
    @Value("${module.future.minimum-range}")
    private float futureMinimumRange;
    @Value("${module.future.maximum-range}")
    private float futureMaximumRange;

    //factories
    @Autowired
    private WalletFactory walletFactory;
    @Autowired
    private StrategyFactory strategyFactory;
    @Autowired
    private MarketFactory marketFactory;

    /**
     *
     */
    public void execute() {
        if (spotStatus) {
            executeSpot();
        }
        if (futureStatus) {
            executeFuture();
        }
    }

    /**
     *
     */
    public void executeSpot() {

        walletFactory.spotWallet().cryptos().forEach(

                (cryptoType, money) -> {

                    if (money < marketFactory.getMinimumStableCoin()) {
                        return;
                    }

                    var vector = VectorType.of(spotVector);
                    var symbol = cryptoType.name() + walletFactory.getStableCoin();
                });
    }

    /**
     *
     *
     * @param symbol
     * @return
     */
    private DecisionType getDecision(String symbol) {

        return strategyFactory.spotStrategy().stream().map(

                strategyType -> {

                    var intervals = strategyType.getIntervals();
                    var ma = strategyType.getMa();

                    switch (strategyType) {
                        case TWO_MA:
                            return strategyFactory.strategy().executeTwoMa(symbol, spotVector, spotMinimumRange, spotMaximumRange, intervals, ma);
                        default:
                            throw new IllegalArgumentException();
                    }

                }).findFirst().orElseThrow(IllegalStateException::new);
    }

    public void executeFuture() {

    }
}

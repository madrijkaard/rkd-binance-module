package com.rkd.binance.executor;

import com.rkd.binance.factory.MarketFactory;
import com.rkd.binance.factory.StrategyFactory;
import com.rkd.binance.factory.WalletFactory;
import com.rkd.binance.strategy.TradeSpotStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class BinanceExecutor {

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

    @Autowired
    private WalletFactory walletFactory;
    @Autowired
    private StrategyFactory strategyFactory;
    @Autowired
    private MarketFactory marketFactory;
    @Autowired
    private TradeSpotStrategy tradeSpotStrategy;

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

        var wallet = walletFactory.spotWallet();
        var spotRank = marketFactory.rankSpot();
        var haveMoney = walletFactory.haveMoney(spotRank);
        var stableCoin = walletFactory.getStableCoin();
        var strategyList = strategyFactory.spotStrategy();

        marketFactory.market().initializeSpot(wallet, spotRank, stableCoin, spotMinimumRange, spotMaximumRange, strategyList, haveMoney);
    }

    /**
     *
     */
    public void executeFuture() {
    }
}

package com.rkd.binance.executor;

import com.rkd.binance.facade.CandlestickFacade;
import com.rkd.binance.facade.MarketFacade;
import com.rkd.binance.facade.StrategyFacade;
import com.rkd.binance.facade.WalletFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;

/**
 * Class responsible for loading the context needed to trade in the spot market.
 */
@Component
public class BinanceExecutor {

    Logger logger = LoggerFactory.getLogger(BinanceExecutor.class);

    @Value("${binance.wallet.standard-fiat-coin}")
    private String fiatCoin;
    @Value("${binance.wallet.standard-stable-coin}")
    private String stableCoin;
    @Value("${binance.market.minimum-stable-coin}")
    private double minimumStableCoin;
    @Value("${binance.market.minimum-fiat-coin}")
    private double minimumFiatCoin;
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
    @Value("#{${module.spot.strategy}}")
    private HashMap<String, Boolean> spotStrategy = new HashMap<>();
    @Value("#{${module.future.strategy}}")
    private HashMap<String, Boolean> futureStrategy = new HashMap<>();
    @Value("${module.spot.rank}")
    private int spotRank;
    @Value("${module.future.rank}")
    private int futureRank;

    @Autowired
    private WalletFacade walletFacade;
    @Autowired
    private MarketFacade marketFacade;
    @Autowired
    private StrategyFacade strategyFacade;
    @Autowired
    private CandlestickFacade candlestickFacade;

    public void execute() {
        if (spotStatus) {
            executeSpot();
        }
        if (futureStatus) {
            executeFuture();
        }
    }

    /**
     * Method responsible for initiating spot trading
     */
    public void executeSpot() {

        var carteira = walletFacade.loadSpot("BRL", "USDT");
        logger.info("--> " + carteira);

        /*var strategyList = spotStrategy.entrySet().stream()
                .filter(Map.Entry::getValue)
                .map(entry -> of(entry.getKey()))
                .collect(Collectors.toList());

        var rank = spotRank == 0 ? new ArrayList<>() : marketFacade.rankMostRelevant(spotRank);
        var stableCoinDouble = walletComponent.spotWallet().stableCoin();
        var minimumStableCoin = walletComponent.getMinimumStableCoin();
        var haveMoney = walletComponent.getWalletFacade().haveMoney(rank, stableCoinDouble, minimumStableCoin);

        marketFactory.market().initializeSpot(wallet, rank, stableCoin, spotMinimumRange, spotMaximumRange, strategyList, haveMoney);*/
    }

    /**
     * Method responsible for initiating future trading
     */
    public void executeFuture() {
    }
}

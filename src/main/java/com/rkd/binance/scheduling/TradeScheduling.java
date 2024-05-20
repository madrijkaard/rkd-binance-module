package com.rkd.binance.scheduling;

import com.rkd.binance.facade.MarketFacade;
import com.rkd.binance.facade.StrategyFacade;
import com.rkd.binance.factory.StrategyFactory;
import com.rkd.binance.factory.WalletFactory;
import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.StrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduling responsible for starting the module trading process with Binance.
 */
@Component
public class TradeScheduling {

    // Factories
    @Autowired
    private StrategyFactory tradeFactory;
    @Autowired
    private WalletFactory walletFactory;

    // Facades
    @Autowired
    private StrategyFacade strategyFacade;
    @Autowired
    private MarketFacade marketFacade;

    /**
     * Method responsible for executing the scheduler according to the period defined in the module's main properties file.
     */
    @Scheduled(fixedRateString = "${strategy.frequency}")
    public void execute() {

        /*var strategies = tradeFactory.strategy();
        var spotWallet = walletFactory.spot();
        var futureWallet = walletFactory.future();
        var stableCoin = walletFactory.getStableCoin();

        strategies.forEach(strategyType ->
                spotWallet.cryptos().forEach((cryptoType, aDouble) -> {
                    var symbol = cryptoType.name() + stableCoin;
                    execute(symbol, strategyType);
                }));*/
    }

    /**
     * @param symbol
     * @param strategyType
     * @return
     */
    public DecisionType execute(String symbol, StrategyType strategyType) {

        var intervals = strategyType.getIntervals();
        var ma = strategyType.getMa();

        switch (strategyType) {
            case TWO_MA:
                return strategyFacade.execute(symbol, intervals, ma);
            default:
                throw new IllegalArgumentException();
        }
    }
}

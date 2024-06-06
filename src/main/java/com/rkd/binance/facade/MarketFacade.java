package com.rkd.binance.facade;

import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.model.KlineModel;
import com.rkd.binance.strategy.*;
import com.rkd.binance.type.CryptoType;
import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.StrategyType;
import com.rkd.binance.type.VectorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Facade responsible for functionalities during the negotiation process.
 */
@Service
public class MarketFacade {

    @Autowired
    private LoadMarketStrategy loadMarketStrategy;
    @Autowired
    private TradeSpotStrategy tradeSpotStrategy;
    @Autowired
    private TradeFutureStrategy tradeFutureStrategy;
    @Autowired
    private DecideStrategy decideStrategy;
    @Autowired
    private PrepareTradeStrategy decideTradeStrategy;
    @Autowired
    private ExecuteSpotStrategy executeSpotStrategy;
    @Autowired
    private MostRelevantCryptoStrategy mostRelevantCryptoStrategy;

    /**
     * Method responsible for obtaining market information based on parameters.
     *
     * @param symbol   currency pair
     * @param interval chart period
     * @param limit    number of items returned, default 500, max 1000
     * @return list of candlesticks
     */
    public List<KlineModel> load(String symbol, String interval, int limit) {
        return loadMarketStrategy.load(symbol, interval, limit);
    }

    /**
     * Method responsible for making a purchase or sale of a cryptocurrency.
     *
     * @param symbol   Example: BTCUSDT, ETHUSDT, ADAUSDT
     * @param money    amount of resources that will be spent on buying or selling
     * @param decision Example: Buy, Sell, Wait
     */
    public void tradeSpot(String symbol, double money, String decision) {
        tradeSpotStrategy.tradeSpot(symbol, money, decision);
    }

    /**
     * @param symbol       The trading pair symbol, e.g., "BTCUSDT"
     * @param margin       The margin type, either "ISOLATED" or "CROSSED"
     * @param leverage     The leverage level to be set
     * @param side         The order side, either "BUY" or "SELL"
     * @param positionSide The position side, e.g., "LONG" or "SHORT"
     * @param order        The order type, e.g., "LIMIT" or "MARKET"
     * @param quantity     The quantity of the order
     */
    public void tradeFuture(String symbol, String margin, int leverage, String side, String positionSide, String order, String quantity) {
        tradeFutureStrategy.tradeFuture(symbol, margin, leverage, side, positionSide, order, quantity);
    }

    /**
     * Method responsible for deciding what to do in the operation.
     *
     * @param sma          short moving average
     * @param lma          long moving average
     * @param vectorType   direction that will be used to execute the gains, example: UP or DOWN
     * @param minimumRange minimum required to execute a trade
     * @param maximumRange maximum required to execute a trade
     * @return BUY, CROSS_BUY, SELL, CROSS_SELL, WAIT
     */
    public DecisionType decide(double sma, double lma, String vector, float minimumRange, float maximumRange) {
        return decideStrategy.decide(sma, lma, vector, minimumRange, maximumRange);
    }

    /**
     *
     * @param symbol
     * @param vector
     * @param minimumRange
     * @param maximumRange
     * @param strategyTypeList
     * @return
     */
    public DecisionType prepare(String symbol, String vector, float minimumRange, float maximumRange, List<StrategyType> strategyTypeList) {
        return decideTradeStrategy.prepare(symbol, vector, minimumRange, maximumRange, strategyTypeList);
    }

    /**
     *
     * @param spotWalletDto
     * @param spotRank
     * @param stableCoin
     * @param spotMinimumRange
     * @param spotMaximumRange
     * @param strategyTypeList
     * @param haveMoney
     */
    public void initializeSpot(SpotWalletDto spotWalletDto, List<CryptoType> spotRank, String stableCoin, float spotMinimumRange, float spotMaximumRange, List<StrategyType> strategyTypeList, boolean haveMoney) {
        executeSpotStrategy.initialize(spotWalletDto, spotRank, stableCoin, spotMinimumRange, spotMaximumRange, strategyTypeList, haveMoney);
    }


    public void initializeFuture(SpotWalletDto spotWalletDto, double minimumStableCoin, String stableCoin, String spotVector, float spotMinimumRange, float spotMaximumRange, List<StrategyType> strategyTypeList) {
    }

    public List<CryptoType> rankMostRelevant(int rank) {
        return mostRelevantCryptoStrategy.rankMostRelevant(rank);
    }
}
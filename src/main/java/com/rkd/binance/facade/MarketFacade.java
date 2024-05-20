package com.rkd.binance.facade;

import com.rkd.binance.model.KlineModel;
import com.rkd.binance.strategy.LoadMarketStrategy;
import com.rkd.binance.strategy.TradeFutureStrategy;
import com.rkd.binance.strategy.TradeSpotStrategy;
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
     * @param interval chart period
     * @param money    amount of resources that will be spent on buying or selling
     * @param decision Example: Buy, Sell, Wait
     */
    public void tradeSpot(String symbol, String interval, double money, String decision) {
        tradeSpotStrategy.tradeSpot(symbol, interval, money, decision);
    }

    /**
     *
     *
     * @param symbol The trading pair symbol, e.g., "BTCUSDT"
     * @param margin The margin type, either "ISOLATED" or "CROSSED"
     * @param leverage The leverage level to be set
     * @param side The order side, either "BUY" or "SELL"
     * @param positionSide The position side, e.g., "LONG" or "SHORT"
     * @param order The order type, e.g., "LIMIT" or "MARKET"
     * @param quantity The quantity of the order
     */
    public void tradeFuture(String symbol, String margin, int leverage, String side, String positionSide, String order, String quantity) {
        tradeFutureStrategy.tradeFuture(symbol, margin, leverage, side, positionSide, order, quantity);
    }
}
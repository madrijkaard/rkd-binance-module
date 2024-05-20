package com.rkd.binance.strategy;

import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.SymbolType;
import com.rkd.binance.type.OrderType;
import com.rkd.binance.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;

import static com.rkd.binance.definition.BinanceDefinition.SPOT_URL_BASE;

/**
 * Class responsible for the buying or selling strategy of a crypto asset.
 */
@Service
public class TradeSpotStrategy {

    @Autowired
    private LastPriceCandlestickStrategy lastPriceCandlestickStrategy;

    /**
     * Method responsible for carrying out purchase and sale operations
     *
     * @param symbol   Example: BTCUSDT, ETHUSDT, ADAUSDT
     * @param interval chart period
     * @param money    amount of resources that will be spent on buying or selling
     * @param decision Example: Buy, Sell, Wait
     * @throws IllegalStateException an error occurred while trying to trade
     */
    public void tradeSpot(String symbol, String interval, double money, String decision) {

        var symbolType = SymbolType.of(symbol);
        var decisionType = DecisionType.of(decision);

        var lastPrice = lastPriceCandlestickStrategy.getLastPrice(symbol, interval);
        var quantity = String.valueOf(calculateQuantityCrypto(money, lastPrice));

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("symbol", symbolType.getSymbol());
        parameters.put("side", decisionType.name());
        parameters.put("type", OrderType.MARKET.name());
        parameters.put("quantity", quantity);

        try {
            RequestUtil.sendSignedRequest(parameters, SPOT_URL_BASE + "/api/v3/order", "POST");
        } catch (Exception e) {
            throw new IllegalStateException("");
        }

        parameters.clear();
    }

    /**
     * Method responsible for calculating the amount of cryptocurrencies possible to purchase with a given amount of money.
     *
     * @param money resource that will be used to buy the cryptocurrency
     * @param price cryptocurrency price now
     * @return amount of cryptocurrencies can I buy
     * @throws IllegalArgumentException money and price are zero
     */
    private double calculateQuantityCrypto(double money, double price) {

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        if (money <= 0 || price <= 0) {
            throw new IllegalArgumentException("Money and price are zero");
        }

        double quantity = money / price;

        return Double.parseDouble(decimalFormat.format(quantity));
    }
}

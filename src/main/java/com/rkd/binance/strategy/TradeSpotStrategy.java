package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.exception.BadRequestException;
import com.rkd.binance.exception.InternalServerErrorException;
import com.rkd.binance.factory.CredentialFactory;
import com.rkd.binance.factory.TradeJournalFactory;
import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.SymbolType;
import com.rkd.binance.util.RequestUtil;
import com.rkd.binance.util.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.HashMap;

import static com.rkd.binance.type.IntervalType.ONE_HOUR;
import static com.rkd.binance.type.OrderType.MARKET;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Class responsible for the buying or selling strategy of a crypto asset.
 */
@Service
public class TradeSpotStrategy {

    @Autowired
    private BinanceSpotClient binanceSpotClient;
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
    public void tradeSpot(String symbol, double money, String decision) {

        long milliseconds = System.currentTimeMillis();

        var symbolType = SymbolType.of(symbol);
        var decisionType = DecisionType.of(decision);
        var typeOrder = MARKET.name();

        var lastPrice = lastPriceCandlestickStrategy.getLastPrice(symbol, ONE_HOUR.getInterval());
        var quantity = String.valueOf(calculateQuantityCrypto(money, lastPrice));

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("symbol", symbolType.getSymbol());
        parameters.put("side", decisionType.name());
        parameters.put("quantity", quantity);
        parameters.put("type", typeOrder);
        parameters.put("timestamp", String.valueOf(milliseconds));

        var queryPath = RequestUtil.joinQueryParameters(parameters);
        var signature = SignatureUtil.getSignature(queryPath, CredentialFactory.getInstance().getSecret());

        String response = null;

        try {

            response = binanceSpotClient.tradeSpot(APPLICATION_JSON_VALUE,
                    CredentialFactory.getInstance().getKey(),
                    symbolType.getSymbol(),
                    decisionType.name(),
                    quantity,
                    typeOrder,
                    String.valueOf(milliseconds),
                    signature);

        } catch (BadRequestException e) {
            System.out.println("-------------> " + e.getCause());
            System.out.println("-------------> " + e.getMessage());
        } catch (InternalServerErrorException e) {

        } catch (Exception e) {

        } finally {
            TradeJournalFactory.getInstance().add(response);
        }
    }

    /**
     * Method responsible for calculating the amount of cryptocurrencies possible to purchase with a given amount of money.
     *
     * @param money resource that will be used to buy the cryptocurrency
     * @param price cryptocurrency price now
     * @return amount of cryptocurrencies can I buy
     * @throws IllegalArgumentException money and price are zero
     */
    private int calculateQuantityCrypto(double money, double price) {

        DecimalFormat decimalFormat = new DecimalFormat("#.##");

        if (money <= 0 || price <= 0) {
            throw new IllegalArgumentException("Money and price are zero");
        }

        double quantity = money / price;

        return (int) quantity;
    }
}

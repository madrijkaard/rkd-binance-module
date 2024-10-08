package com.rkd.binance.strategy;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.component.CredentialComponent;
import com.rkd.binance.component.TradeJournalComponent;
import com.rkd.binance.exception.CustomFeignException;
import com.rkd.binance.response.ErrorTradeResponse;
import com.rkd.binance.response.TradeSpotResponse;
import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.SymbolType;
import com.rkd.binance.util.EnumUtil;
import com.rkd.binance.util.RequestUtil;
import com.rkd.binance.util.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static com.rkd.binance.definition.ExceptionDefinition.MONEY_AND_PRICE_ZERO;
import static com.rkd.binance.type.OrderType.MARKET;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
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
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private CredentialComponent credentialComponent;

    /**
     * Method responsible for executing purchase and sale operations.
     *
     * @param symbol   example: BTCUSDT, ETHUSDT, ADAUSDT
     * @param money    amount of resources that will be spent on buying or selling
     * @param decision example: Buy, Sell, Wait
     * @throws CustomFeignException an error occurred while trying to trade
     */
    public void tradeSpot(String symbol, double money, String decision) {

        var apiKey = credentialComponent.getKey();
        var symbolType = EnumUtil.of(SymbolType.class, symbol);
        var decisionType = DecisionType.of(decision);
        var typeOrder = MARKET.name();
        long milliseconds = System.currentTimeMillis();

        var lastPrice = lastPriceCandlestickStrategy.getLastPrice(symbol);
        var quantity = String.valueOf(calculateQuantityCrypto(money, lastPrice));

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("symbol", symbolType.getSymbol());
        parameters.put("side", decisionType.name());
        parameters.put("quantity", quantity);
        parameters.put("type", typeOrder);
        parameters.put("timestamp", String.valueOf(milliseconds));

        var queryPath = RequestUtil.joinQueryParameters(parameters);
        var signature = SignatureUtil.getSignature(queryPath, credentialComponent.getSecret());

        try {

            var response = binanceSpotClient.tradeSpot(APPLICATION_JSON_VALUE,
                    apiKey,
                    symbolType.getSymbol(),
                    decisionType.name(),
                    quantity,
                    typeOrder,
                    String.valueOf(milliseconds),
                    signature);

            var tradeSpotResponse = objectMapper.readValue(response, TradeSpotResponse.class);
            TradeJournalComponent.getInstance().getTrades().add(tradeSpotResponse);

        } catch (CustomFeignException cfe) {
            var error = cfe.getErrorTradeResponse();
            TradeJournalComponent.getInstance().getErrors().add(error);
        } catch (JsonProcessingException jpe) {
            var error = jpe.getMessage();
            ErrorTradeResponse errorTradeResponse = new ErrorTradeResponse(INTERNAL_SERVER_ERROR.value(), error);
            TradeJournalComponent.getInstance().getErrors().add(errorTradeResponse);
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

        if (money <= 0 || price <= 0) {
            throw new IllegalArgumentException(MONEY_AND_PRICE_ZERO);
        }

        double quantity = money / price;
        return (int) quantity;
    }
}

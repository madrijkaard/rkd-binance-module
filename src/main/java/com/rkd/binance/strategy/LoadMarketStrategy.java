package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.converter.KlineConverter;
import com.rkd.binance.model.KlineModel;
import com.rkd.binance.type.IntervalType;
import com.rkd.binance.type.SymbolType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rkd.binance.definition.ExceptionDefinition.ENDPOINT_DID_NOT_RETURN_RESULTS;
import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

/**
 * Class responsible for seeking market information according to pre-defined parameters.
 */
@Service
public class LoadMarketStrategy {

    private static final int INVALID_ZERO_LIMIT = 0;
    private static final int MAX_LIMIT_THOUSAND = 1000;

    @Autowired
    private BinanceSpotClient binanceClient;
    @Autowired
    private KlineConverter klineConverter;

    /**
     * Method responsible for obtaining market information based on parameters.
     *
     * @param symbol   currency pair
     * @param interval chart period
     * @param limit    number of items returned, default 500, max 1000
     * @return list of candlesticks
     * @throws IllegalArgumentException api return limit has an invalid value
     * @throws IllegalStateException    the service did not return results
     */
    public List<KlineModel> load(String symbol, String interval, int limit) {

        var symbolType = SymbolType.of(symbol).getSymbol();
        var intervalType = IntervalType.of(interval).getInterval();

        if (limit <= INVALID_ZERO_LIMIT || limit > MAX_LIMIT_THOUSAND) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("limit"));
        }

        var klineList = binanceClient.listKlines(symbolType, intervalType, limit);

        if (klineList == null || klineList.isEmpty()) {
            throw new IllegalStateException(ENDPOINT_DID_NOT_RETURN_RESULTS.concat("/api/v3/uiKlines"));
        }

        return klineConverter.convertOf(klineList);
    }
}

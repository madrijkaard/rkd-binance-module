package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.converter.KlineConverter;
import com.rkd.binance.model.KlineModel;
import com.rkd.binance.type.IntervalType;
import com.rkd.binance.type.SymbolType;
import com.rkd.binance.util.EnumUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

/**
 * Class responsible for seeking market information according to pre-defined parameters.
 */
@Service
public class LoadMarketStrategy {

    private static final int INVALID_ZERO_LIMIT = 0;
    private static final int MAX_LIMIT_THOUSAND = 1000;

    private BinanceSpotClient binanceClient;
    private KlineConverter klineConverter;

    @Autowired
    public LoadMarketStrategy(BinanceSpotClient binanceClient, KlineConverter klineConverter) {
        this.binanceClient = binanceClient;
        this.klineConverter = klineConverter;
    }

    /**
     * Method responsible for obtaining market information based on parameters.
     *
     * @param symbol   currency pair (e.g., BTC_USDT, ETH_BTC, LINK_USDT)
     * @param interval chart period (e.g., ONE_HOUR, ONE_DAY, ONE_WEEK)
     * @param limit    number of items returned, default 500, max 1000
     * @return list of candlesticks
     * @throws IllegalStateException the service did not return results
     */
    public List<KlineModel> load(String symbol, String interval, int limit) {

        var symbolType = EnumUtil.of(SymbolType.class, symbol).getSymbol();
        var intervalType = EnumUtil.of(IntervalType.class, interval).getInterval();

        if (limit <= INVALID_ZERO_LIMIT || limit > MAX_LIMIT_THOUSAND) {
            throw new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("limit"));
        }

        var klineList = binanceClient.listKlines(symbolType, intervalType, limit);

        return klineConverter.convertOf(klineList);
    }
}

package com.rkd.binance.strategy;

import com.rkd.binance.model.KlineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rkd.binance.definition.ExceptionDefinition.CANDLESTICK_LIST_IS_NULL;
import static com.rkd.binance.type.IntervalType.ONE_HOUR;

/**
 * Class responsible for the strategy of obtaining the current price of the asset.
 */
@Service
public class LastPriceCandlestickStrategy {

    public static final int LIMIT_DEFAULT = 1;

    private LoadMarketStrategy loadMarketStrategy;

    @Autowired
    public LastPriceCandlestickStrategy(LoadMarketStrategy loadMarketStrategy) {
        this.loadMarketStrategy = loadMarketStrategy;
    }

    /**
     * Method responsible for obtaining the current price of the asset.
     *
     * @param symbol currency pair
     * @return current asset price
     * @throws IllegalStateException candlestick list is null or empty
     */
    public double getLastPrice(String symbol) {

        var klineModelList = loadMarketStrategy.load(symbol, ONE_HOUR.name(), LIMIT_DEFAULT);

        if (klineModelList == null) {
            throw new IllegalStateException(CANDLESTICK_LIST_IS_NULL);
        }

        return klineModelList
                .stream()
                .findFirst()
                .map(KlineModel::getClosePrice)
                .map(Double::parseDouble)
                .orElseThrow(IllegalStateException::new);
    }
}

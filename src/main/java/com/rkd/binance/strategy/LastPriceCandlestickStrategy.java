package com.rkd.binance.strategy;

import com.rkd.binance.model.KlineModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.rkd.binance.definition.BinanceDefinition.LIMIT_DEFAULT;
import static com.rkd.binance.definition.ExceptionDefinition.MORE_THAN_ONE_CANDLESTICK_FOUND;

/**
 * Class responsible for the strategy of obtaining the current price of the asset.
 */
@Service
public class LastPriceCandlestickStrategy {

    @Autowired
    private LoadMarketStrategy loadMarketStrategy;

    /**
     * Method responsible for obtaining the current price of the asset.
     *
     * @param symbol currency pair
     * @param interval chart period
     * @return current asset price
     * @throws IllegalArgumentException it is expected that there is only one candlestick
     * @throws IllegalArgumentException
     */
    public double getLastPrice(String symbol, String interval) {

        var klineModelList = loadMarketStrategy.load(symbol, interval, LIMIT_DEFAULT);

        if (klineModelList.size() != 1) {
            throw new IllegalArgumentException(MORE_THAN_ONE_CANDLESTICK_FOUND);
        }

        return klineModelList
                .stream()
                .findFirst()
                .map(KlineModel::getClosePrice)
                .map(Double::parseDouble)
                .orElseThrow(IllegalArgumentException::new);
    }
}

package com.rkd.binance.converter;

import com.rkd.binance.model.KlineModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class responsible for converting candlesticks.
 */
@Component
public class KlineConverter {

    /**
     * Method responsible for converting a list containing a String list into a candlestick model.
     *
     * Input example:
     *
     * [
     *      [
     *          1499040000000,      // Kline open time
     *          "0.01634790",       // Open price
     *          "0.80000000",       // High price
     *          "0.01575800",       // Low price
     *          "0.01577100",       // Close price
     *          "148976.11427815",  // Volume
     *          1499644799999,      // Kline Close time
     *          "2434.19055334",    // Quote asset volume
     *          308,                // Number of trades
     *          "1756.87402397",    // Taker buy base asset volume
     *          "28.46694368",      // Taker buy quote asset volume
     *          "0"                 // Unused field, ignore.
     *      ]
     * ]
     *
     * @param klineList list of String following the structure above
     * @return candlestick model
     */
    public List<KlineModel> convertOf(List<List<String>> klineList) {
        return klineList.stream().map(this::buildKlineModel).collect(Collectors.toList());
    }

    /**
     * Method responsible for building a candlestick model.
     *
     * @param kline candlestick
     * @return candlestick model
     */
    private KlineModel buildKlineModel(List<String> kline) {
        return new KlineModel(
                Instant.ofEpochMilli(Long.parseLong(kline.get(0))),
                Instant.ofEpochMilli(Long.parseLong(kline.get(6))),
                0,
                kline.get(1),
                kline.get(2),
                kline.get(3),
                kline.get(4),
                kline.get(5),
                kline.get(7),
                Integer.parseInt(kline.get(8)),
                kline.get(9),
                kline.get(10));
    }
}

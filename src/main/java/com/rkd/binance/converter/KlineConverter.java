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
     * Method responsible for converting a list of candlesticks into a model.
     *
     * @param klineList list of candlesticks
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

        if (kline.isEmpty()) {
            return null;
        }

        return new KlineModel(
                Instant.ofEpochMilli(Long.parseLong(kline.get(0))),
                Instant.ofEpochMilli(Long.parseLong(kline.get(6))),
                0,
                "",
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

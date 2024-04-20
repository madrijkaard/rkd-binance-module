package com.rkd.binance.converter;

import com.rkd.binance.model.KlineModel;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class KlineConverter {

    public List<KlineModel> convertOf(List<List<String>> klineList) {
        return klineList.stream().map(this::buildKlineModel).collect(Collectors.toList());
    }

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

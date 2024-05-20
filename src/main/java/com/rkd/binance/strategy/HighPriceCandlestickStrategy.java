package com.rkd.binance.strategy;

import com.rkd.binance.model.KlineModel;
import com.rkd.binance.repository.KlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Comparator;

@Service
public class HighPriceCandlestickStrategy {
    @Autowired
    private KlineRepository klineRepository;

    public String getHighPrice(Instant openTime, Instant closeTime) {
        var klineModelList = klineRepository.findByOpenTimeAndCloseTime(openTime, closeTime);
        return klineModelList.stream().max(Comparator.comparing(KlineModel::getHighPrice)).map(KlineModel::getHighPrice).orElse("0");
    }
}

package com.rkd.binance.strategy;

import com.rkd.binance.model.KlineModel;
import com.rkd.binance.repository.KlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class OpenPriceCandlestickStrategy {
    @Autowired
    private KlineRepository klineRepository;

    public String getOpenPrice(Instant openTime, Instant closeTime) {
        return klineRepository.findLastByOpenTimeAndCloseTime(openTime, closeTime)
                .stream().map(KlineModel::getOpenPrice).findFirst().orElse("0");
    }
}

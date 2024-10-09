package com.rkd.binance.facade;

import com.rkd.binance.model.KlineModel;
import com.rkd.binance.strategy.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class CandlestickFacade {

    @Autowired
    private HighPriceCandlestickStrategy highPriceCandlestickStrategy;
    @Autowired
    private LowPriceCandlestickStrategy lowPriceCandlestickStrategy;
    @Autowired
    private OpenPriceCandlestickStrategy openPriceCandlestickStrategy;
    @Autowired
    private ClosePriceCandlestickStrategy closePriceCandlestickStrategy;
    @Autowired
    private SaveMarketStrategy saveMarketStrategy;
    @Autowired
    private LastPriceCandlestickStrategy lastPriceCandlestickStrategy;

    public String getHighPrice(Instant openTime, Instant closeTime) {
        return highPriceCandlestickStrategy.getHighPrice(openTime, closeTime);
    }

    public String getLowPrice(Instant openTime, Instant closeTime) {
        return lowPriceCandlestickStrategy.getLowPrice(openTime, closeTime);
    }

    public String getOpenPrice(Instant openTime, Instant closeTime) {
        return openPriceCandlestickStrategy.getOpenPrice(openTime, closeTime);
    }

    public String getClosePrice(Instant openTime, Instant closeTime) {
        return closePriceCandlestickStrategy.getClosePrice(openTime, closeTime);
    }

    public void saveMarket(List<KlineModel> klineModelList) {
        saveMarketStrategy.saveMarket(klineModelList);
    }

    public double getLastPrice(String symbol) {
        return lastPriceCandlestickStrategy.getLastPrice(symbol);
    }
}

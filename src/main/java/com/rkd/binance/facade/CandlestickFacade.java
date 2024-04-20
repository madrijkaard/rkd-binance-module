package com.rkd.binance.facade;

import com.rkd.binance.strategy.candlestick.ClosePriceCandlestickStrategy;
import com.rkd.binance.strategy.candlestick.HighPriceCandlestickStrategy;
import com.rkd.binance.strategy.candlestick.LowPriceCandlestickStrategy;
import com.rkd.binance.strategy.candlestick.OpenPriceCandlestickStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

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
}

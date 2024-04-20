package com.rkd.binance.aspect;

import com.rkd.binance.facade.CandlestickFacade;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Aspect
@Component
public class MarketAspect {
    @Autowired
    private CandlestickFacade metricFacade;
    private final MeterRegistry meterRegistry;
    private final AtomicReference<Double> highPriceValue = new AtomicReference<>(0.0);
    private final AtomicReference<Double> lowPriceValue = new AtomicReference<>(0.0);
    private final AtomicReference<Double> openPriceValue = new AtomicReference<>(0.0);
    private final AtomicReference<Double> closePriceValue = new AtomicReference<>(0.0);

    @Autowired
    public MarketAspect(MeterRegistry meterRegistry) {
        this.meterRegistry = meterRegistry;
    }

    @Before("execution(* com.rkd.binance.producer.KlineProducer.sendMessage(..)) && args(klineList)")
    public void beforeSendMessage(JoinPoint joinPoint, List<List<String>> klineList) {

        var openTimeString =
                klineList.stream().findFirst().map(k -> k.get(0)).orElseThrow(IllegalArgumentException::new);
        var closeTimeString =
                klineList.stream().findFirst().map(k -> k.get(6)).orElseThrow(IllegalArgumentException::new);

        Instant openTime = Instant.ofEpochMilli(Long.parseLong(openTimeString));
        Instant closeTime = Instant.ofEpochMilli(Long.parseLong(closeTimeString));

        var highPrice = metricFacade.getHighPrice(openTime, closeTime);
        var lowPrice = metricFacade.getLowPrice(openTime, closeTime);
        var openPrice = metricFacade.getOpenPrice(openTime, closeTime);
        var closePrice = metricFacade.getClosePrice(openTime, closeTime);

        highPriceValue.set(Double.parseDouble(highPrice));
        lowPriceValue.set(Double.parseDouble(lowPrice));
        openPriceValue.set(Double.parseDouble(openPrice));
        closePriceValue.set(Double.parseDouble(closePrice));

        Gauge.builder("market.high.price", highPriceValue, AtomicReference::get).tags(Tags.empty()).register(meterRegistry);
        Gauge.builder("market.low.price", lowPriceValue, AtomicReference::get).tags(Tags.empty()).register(meterRegistry);
        Gauge.builder("market.open.price", openPriceValue, AtomicReference::get).tags(Tags.empty()).register(meterRegistry);
        Gauge.builder("market.close.price", closePriceValue, AtomicReference::get).tags(Tags.empty()).register(meterRegistry);
    }
}

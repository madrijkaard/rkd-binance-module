package com.rkd.binance.service;

import com.rkd.binance.client.BinanceClient;
import com.rkd.binance.producer.KlineProducer;
import com.rkd.binance.type.IntervalType;
import com.rkd.binance.type.SymbolType;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.rkd.binance.definition.ExceptionDefinition.ENDPOINT_DID_NOT_RETURN_RESULTS;
import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

/**
 * Class responsible for seeking market information according to pre-defined parameters.
 */
@Service
public class KlineService {

    @Value("${binance.market.symbol}")
    private String symbol;
    @Value("${binance.market.interval}")
    private String interval;
    @Value("${binance.market.limit}")
    private int limit;

    @Autowired
    private BinanceClient klineClient;
    @Autowired
    private KlineProducer cassandraProducer;
    @Autowired
    private Tracer tracer;

    /**
     * Method responsible for seeking updated market information.
     *
     * @return list of candlesticks
     */
    public void listKlines() {

        Span span = tracer.buildSpan("list klines").start();

        var symbolType = SymbolType.of(symbol).getSymbol();
        var intervalType = IntervalType.of(interval).getInterval();

        if (limit == 0) {
            throw new IllegalArgumentException(String.valueOf(limit).concat(ILLEGAL_ARGUMENT));
        }

        var klineList = klineClient.listKlines(symbolType, intervalType, limit);

        if (klineList.isEmpty()) {
            throw new IllegalStateException(ENDPOINT_DID_NOT_RETURN_RESULTS.concat("/uiKlines"));
        }

        cassandraProducer.sendMessage(klineList);

        span.finish();
    }
}

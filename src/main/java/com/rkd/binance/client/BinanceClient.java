package com.rkd.binance.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * Interface responsible for calling the Binance services.
 *
 * @see <a href="https://github.com/binance/binance-spot-api-docs/tree/master">Official Documentation for the Binance APIs and Streams</a>
 */
@FeignClient(value = "kline", url = "${binance.api.url}")
public interface BinanceClient {

    /**
     * Method responsible for listing candlesticks on a chart period.
     *
     * @param symbol currency pair
     * @param interval chart period
     * @param limit candlesticks that will be returned
     * @return list of candles and transaction volumes
     */
    @RequestMapping(method = GET, value = "/uiKlines")
    List<List<String>> listKlines(@RequestParam(name = "symbol") String symbol,
                                         @RequestParam(name = "interval") String interval,
                                         @RequestParam(name = "limit") int limit);
}

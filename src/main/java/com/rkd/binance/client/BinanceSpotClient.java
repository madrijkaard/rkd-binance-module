package com.rkd.binance.client;

import com.rkd.binance.config.FeignConfig;
import com.rkd.binance.dto.BalanceSpotDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * Interface responsible for calling the Binance services.
 *
 * @see <a href="https://github.com/binance/binance-spot-api-docs/tree/master">Official Documentation for the Binance APIs and Streams</a>
 */
@FeignClient(value = "spot", url = "${binance.url.spot}", configuration = FeignConfig.class)
public interface BinanceSpotClient {

    /**
     * Method responsible for listing candlesticks on a chart period.
     * <p>
     * Return example:
     * <p>
     * [
     * [
     * 1499040000000,      // Kline open time
     * "0.01634790",       // Open price
     * "0.80000000",       // High price
     * "0.01575800",       // Low price
     * "0.01577100",       // Close price
     * "148976.11427815",  // Volume
     * 1499644799999,      // Kline Close time
     * "2434.19055334",    // Quote asset volume
     * 308,                // Number of trades
     * "1756.87402397",    // Taker buy base asset volume
     * "28.46694368",      // Taker buy quote asset volume
     * "0"                 // Unused field, ignore
     * ]
     * ]
     *
     * @param symbol   currency pair
     * @param interval chart period
     * @param limit    number of items returned, default 500, max 1000
     * @return list of candlesticks
     */
    @RequestMapping(method = GET, value = "/api/v3/uiKlines")
    List<List<String>> listKlines(@RequestParam(name = "symbol") String symbol,
                                  @RequestParam(name = "interval") String interval,
                                  @RequestParam(name = "limit") int limit);

    @RequestMapping(method = POST, value = "/sapi/v3/asset/getUserAsset")
    List<BalanceSpotDto> getBalanceSpot(@RequestHeader(name = "Content-Type") String content,
                                        @RequestHeader(name = "X-MBX-APIKEY") String key,
                                        @RequestParam(name = "timestamp") String timestamp,
                                        @RequestParam(name = "signature") String signature);

    @RequestMapping(method = POST, value = "/api/v3/order")
    String tradeSpot(@RequestHeader(name = "Content-Type") String content,
                                    @RequestHeader(name = "X-MBX-APIKEY") String key,
                                    @RequestParam(name = "symbol") String symbol,
                                    @RequestParam(name = "side") String side,
                                    @RequestParam(name = "quantity") String quantity,
                                    @RequestParam(name = "type") String type,
                                    @RequestParam(name = "timestamp") String timestamp,
                                    @RequestParam(name = "signature") String signature);
}

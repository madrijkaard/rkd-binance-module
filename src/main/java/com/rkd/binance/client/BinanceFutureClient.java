package com.rkd.binance.client;

import com.rkd.binance.dto.BalanceCrossMarginDto;
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
@FeignClient(value = "future", url = "${binance.url.future}")
public interface BinanceFutureClient {

    /**
     * Retrieves the balance of the futures account.
     *
     * @param content   The content type, typically "application/json".
     * @param key       The API key for authentication.
     * @param timestamp The request timestamp in milliseconds.
     * @param signature The request signature for security.
     * @return A list of {@link BalanceCrossMarginDto} representing the balance.
     */
    @RequestMapping(method = GET, value = "/fapi/v2/balance")
    List<BalanceCrossMarginDto> getBalanceFuture(@RequestHeader(name = "Content-Type") String content,
                                                 @RequestHeader(name = "X-MBX-APIKEY") String key,
                                                 @RequestParam(name = "timestamp") String timestamp,
                                                 @RequestParam(name = "signature") String signature);

    /**
     * Changes the margin type for a specified symbol.
     *
     * @param content    The content type, typically "application/json".
     * @param key        The API key for authentication.
     * @param symbol     The trading pair symbol, e.g., "BTCUSDT".
     * @param marginType The margin type, either "ISOLATED" or "CROSSED".
     * @param timestamp  The request timestamp in milliseconds.
     * @param signature  The request signature for security.
     */
    @RequestMapping(method = POST, value = "/fapi/v1/marginType")
    void changeMarginType(@RequestHeader(name = "Content-Type") String content,
                          @RequestHeader(name = "X-MBX-APIKEY") String key,
                          @RequestParam(name = "symbol") String symbol,
                          @RequestParam(name = "marginType") String marginType,
                          @RequestParam(name = "timestamp") String timestamp,
                          @RequestParam(name = "signature") String signature);

    /**
     * Sets the leverage for a specified symbol.
     *
     * @param content   The content type, typically "application/json".
     * @param key       The API key for authentication.
     * @param symbol    The trading pair symbol, e.g., "BTCUSDT".
     * @param leverage  The leverage level to be set.
     * @param timestamp The request timestamp in milliseconds.
     * @param signature The request signature for security.
     */
    @RequestMapping(method = POST, value = "/fapi/v1/leverage")
    void setLeverage(@RequestHeader(name = "Content-Type") String content,
                          @RequestHeader(name = "X-MBX-APIKEY") String key,
                          @RequestParam(name = "symbol") String symbol,
                          @RequestParam(name = "leverage") String leverage,
                          @RequestParam(name = "timestamp") String timestamp,
                          @RequestParam(name = "signature") String signature);

    /**
     * Places an order in the futures market.
     *
     * @param content      The content type, typically "application/json".
     * @param key          The API key for authentication.
     * @param symbol       The trading pair symbol, e.g., "BTCUSDT".
     * @param side         The order side, either "BUY" or "SELL".
     * @param positionSide The position side, e.g., "LONG" or "SHORT".
     * @param type         The order type, e.g., "LIMIT" or "MARKET".
     * @param quantity     The quantity of the order.
     * @param timestamp    The request timestamp in milliseconds.
     * @param signature    The request signature for security.
     */
    @RequestMapping(method = POST, value = "/fapi/v1/order")
    void placeOrder(@RequestHeader(name = "Content-Type") String content,
                    @RequestHeader(name = "X-MBX-APIKEY") String key,
                    @RequestParam(name = "symbol") String symbol,
                    @RequestParam(name = "side") String side,
                    @RequestParam(name = "positionSide") String positionSide,
                    @RequestParam(name = "type") String type,
                    @RequestParam(name = "quantity") String quantity,
                    @RequestParam(name = "timestamp") String timestamp,
                    @RequestParam(name = "signature") String signature);
}

package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.converter.KlineConverter;
import config.DynamicPropertyConfig;
import config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.rkd.binance.type.IntervalType.ONE_HOUR;
import static com.rkd.binance.type.SymbolType.BTC_USDT;
import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
public class LastPriceCandlestickStrategyTest extends DynamicPropertyConfig {

    public static final int LIMIT_DEFAULT = 1;

    @Autowired
    private BinanceSpotClient binanceClient;

    private KlineConverter klineConverter;

    private LoadMarketStrategy loadMarketStrategy;

    private LastPriceCandlestickStrategy lastPriceCandlestickStrategy;

    @Mock
    private LoadMarketStrategy loadMarketStrategyMock;

    @BeforeEach
    public void init() {
        klineConverter = new KlineConverter();
        loadMarketStrategy = new LoadMarketStrategy(binanceClient, klineConverter);
        lastPriceCandlestickStrategy = new LastPriceCandlestickStrategy(loadMarketStrategy);
    }

    @Test
    @DisplayName("Tests whether the current price service returns a valid price")
    public void getLastPrice() {
        var actual = lastPriceCandlestickStrategy.getLastPrice(BTC_USDT.name());
        assertTrue(actual > 0);
    }

    @Test
    @DisplayName("Tests when the current price service returns empty")
    public void getLastPriceExceptionEmptyList() {
        when(loadMarketStrategyMock.load(BTC_USDT.name(), ONE_HOUR.name(), LIMIT_DEFAULT)).thenReturn(emptyList());
        lastPriceCandlestickStrategy = new LastPriceCandlestickStrategy(loadMarketStrategyMock);
        assertThrows(IllegalStateException.class, () -> lastPriceCandlestickStrategy.getLastPrice(BTC_USDT.name()));
    }

    @Test
    @DisplayName("Tests when the current price service returns null")
    public void getLastPriceExceptionNull() {
        when(loadMarketStrategyMock.load(BTC_USDT.name(), ONE_HOUR.name(), LIMIT_DEFAULT)).thenReturn(null);
        lastPriceCandlestickStrategy = new LastPriceCandlestickStrategy(loadMarketStrategyMock);
        assertThrows(IllegalStateException.class, () -> lastPriceCandlestickStrategy.getLastPrice(BTC_USDT.name()));
    }
}

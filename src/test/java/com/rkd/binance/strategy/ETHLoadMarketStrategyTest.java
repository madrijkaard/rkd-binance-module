package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.converter.KlineConverter;
import com.rkd.binance.type.IntervalType;
import com.rkd.binance.type.SymbolType;
import config.DynamicPropertyConfig;
import config.TestConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
public class ETHLoadMarketStrategyTest extends DynamicPropertyConfig {

    private static final int ONE_LIMIT = 1;
    private static final int FIFTY_LIMIT = 50;
    private static final int HUNDRED_LIMIT = 100;
    private static final int TWO_HUNDRED_LIMIT = 200;

    @Autowired
    private BinanceSpotClient binanceClient;

    private KlineConverter klineConverter;

    private LoadMarketStrategy loadMarketStrategy;

    @BeforeEach
    public void init() {
        klineConverter = new KlineConverter();
        loadMarketStrategy = new LoadMarketStrategy(binanceClient, klineConverter);
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest1h() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_HOUR;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), ONE_LIMIT);
        assertEquals(ONE_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest1d() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_DAY;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), ONE_LIMIT);
        assertEquals(ONE_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest1w() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_WEEK;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), ONE_LIMIT);
        assertEquals(ONE_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest50h() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_HOUR;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), FIFTY_LIMIT);
        assertEquals(FIFTY_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest50d() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_DAY;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), FIFTY_LIMIT);
        assertEquals(FIFTY_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest50w() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_WEEK;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), FIFTY_LIMIT);
        assertEquals(FIFTY_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest100h() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_HOUR;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), HUNDRED_LIMIT);
        assertEquals(HUNDRED_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest100d() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_DAY;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), HUNDRED_LIMIT);
        assertEquals(HUNDRED_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest100w() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_WEEK;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), HUNDRED_LIMIT);
        assertEquals(HUNDRED_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest200h() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_HOUR;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), TWO_HUNDRED_LIMIT);
        assertEquals(TWO_HUNDRED_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest200d() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_DAY;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), TWO_HUNDRED_LIMIT);
        assertEquals(TWO_HUNDRED_LIMIT, actual.size());
    }

    @Test
    @DisplayName("Test validates the limit of candles returned by Binance")
    public void limitTest200w() {
        var symbol = SymbolType.ETH_USDT;
        var interval = IntervalType.ONE_WEEK;
        var actual = loadMarketStrategy.load(symbol.name(), interval.name(), TWO_HUNDRED_LIMIT);
        assertEquals(TWO_HUNDRED_LIMIT, actual.size());
    }
}

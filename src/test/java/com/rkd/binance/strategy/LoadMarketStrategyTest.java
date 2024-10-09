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
import util.TestUtil;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
public class LoadMarketStrategyTest extends DynamicPropertyConfig {

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
    @DisplayName("Test when 'symbol' is not provided")
    public void testSymbolNotProvided() {

        var interval = TestUtil.generateRandomEnum(IntervalType.class);
        var limit = TestUtil.generateRandomInteger();

        var actual = assertThrows(IllegalArgumentException.class, () -> loadMarketStrategy.load(null, interval.name(), limit));
        assertEquals("Invalid argument for SymbolType: null", actual.getMessage());
    }

    @Test
    @DisplayName("Test when 'interval' is not provided")
    public void testIntervalNotProvided() {

        var symbol = TestUtil.generateRandomEnum(SymbolType.class);
        var limit = TestUtil.generateRandomInteger();

        var actual = assertThrows(IllegalArgumentException.class, () -> loadMarketStrategy.load(symbol.name(), null, limit));
        assertEquals("Invalid argument for IntervalType: null", actual.getMessage());
    }

    @Test
    @DisplayName("Test when 'limit' is not valid with -1")
    public void testLimitIsNotValidWithMinus1() {

        var symbol = TestUtil.generateRandomEnum(SymbolType.class);
        var interval = TestUtil.generateRandomEnum(IntervalType.class);

        var actual = assertThrows(IllegalArgumentException.class, () -> loadMarketStrategy.load(symbol.name(), interval.name(), -1));
        assertEquals("Invalid argument: limit", actual.getMessage());
    }

    @Test
    @DisplayName("Test when 'limit' is not valid with 0")
    public void testLimitIsNotValidWith0() {

        var symbol = TestUtil.generateRandomEnum(SymbolType.class);
        var interval = TestUtil.generateRandomEnum(IntervalType.class);

        var actual = assertThrows(IllegalArgumentException.class, () -> loadMarketStrategy.load(symbol.name(), interval.name(), 0));
        assertEquals("Invalid argument: limit", actual.getMessage());
    }

    @Test
    @DisplayName("Test when 'limit' is not valid with 1001")
    public void testLimitIsNotValidWith1001() {

        var symbol = TestUtil.generateRandomEnum(SymbolType.class);
        var interval = TestUtil.generateRandomEnum(IntervalType.class);

        var actual = assertThrows(IllegalArgumentException.class, () -> loadMarketStrategy.load(symbol.name(), interval.name(), 1001));
        assertEquals("Invalid argument: limit", actual.getMessage());
    }
}

package com.rkd.binance.strategy;

import config.TestConfig;
import factory.WiremockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.rkd.binance.type.IntervalType.ONE_HOUR;
import static com.rkd.binance.type.SymbolType.BTC_USDT;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
public class LastPriceCandlestickStrategyTest {

    @Autowired
    private LoadMarketStrategy loadMarketStrategy;

    private LastPriceCandlestickStrategy lastPriceCandlestickStrategy;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        var port = WiremockFactory.getInstance().getWireMockServer().port();
        registry.add("binance.url.spot", () -> "http://localhost:" + port);
    }

    @BeforeEach
    public void init() {
        lastPriceCandlestickStrategy = new LastPriceCandlestickStrategy(loadMarketStrategy);
    }

    @Test
    public void getBalance() {
        //var actual = lastPriceCandlestickStrategy.getLastPrice(BTC_USDT.getSymbol(), ONE_HOUR.getInterval());
    }
}

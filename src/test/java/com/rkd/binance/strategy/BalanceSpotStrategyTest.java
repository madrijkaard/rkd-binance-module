package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.type.CryptoType;
import config.TestConfig;
import factory.WiremockFactory;
import fake.CredentialComponentFake;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Import(TestConfig.class)
@ExtendWith(SpringExtension.class)
public class BalanceSpotStrategyTest {

    @Autowired
    private CredentialComponentFake credentialComponentFake;
    @Autowired
    private BinanceSpotClient binanceSpotClient;

    private BalanceSpotStrategy balanceSpotStrategy;

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        var port = WiremockFactory.getInstance().getWireMockServer().port();
        registry.add("binance.url.spot", () -> "http://localhost:" + port);
    }

    @BeforeEach
    public void init() {
        balanceSpotStrategy = new BalanceSpotStrategy(credentialComponentFake, binanceSpotClient);
    }

    @Test
    public void getBalance() {

        var actual = balanceSpotStrategy.getBalance("BRL", "USDT");

        var fiatCoin = actual.fiatCoin();
        assertEquals(1500.0, fiatCoin);

        var stableCoin = actual.stableCoin();
        assertEquals(500.0, stableCoin);

        var btc = actual.cryptos().get(CryptoType.BTC);
        assertEquals(5.0, btc);

        var eth = actual.cryptos().get(CryptoType.ETH);
        assertEquals(8.0, eth);
    }
}

package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.factory.TradeJournalFactory;
import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.SymbolType;
import com.rkd.binance.util.TestUtil;
import config.TestConfig;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.UUID;

import static com.rkd.binance.type.OrderType.MARKET;
import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Import(TestConfig.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class TradeSpotStrategyTest {

    @InjectMocks
    private TradeSpotStrategy tradeSpotStrategy;

    @Mock
    private LastPriceCandlestickStrategy lastPriceCandlestickStrategy;

    @Mock
    private BinanceSpotClient binanceSpotClient;

    @Test
    @DisplayName("Test performs a mock purchase using Wiremock and checks whether the purchase return was recorded by the trade journal")
    public void successfulSpotTrade() throws IOException, InterruptedException {

        var symbol = SymbolType.getRandomSymbol().getSymbol();
        var money = TestUtil.generateRandomMoney();
        var decision = DecisionType.getRandomDecision().name();
        var lastPrice = TestUtil.generateRandomPrice();

        when(lastPriceCandlestickStrategy.getLastPrice(eq(symbol), anyString())).thenReturn(lastPrice);
        when(binanceSpotClient.tradeSpot(eq(APPLICATION_JSON_VALUE), any(), eq(symbol), eq(decision), any(),
                eq(MARKET.name()), any(), any())).thenReturn(callTradeSpotMocked().body());

        tradeSpotStrategy.tradeSpot(symbol, money, decision);

        assertEquals(1, TradeJournalFactory.getInstance().getTrades().size());
    }

    private HttpResponse<String> callTradeSpotMocked() throws IOException, InterruptedException {

        HttpClient client = newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:8080/api/v3/order"))
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE)
                .header("X-MBX-APIKEY", UUID.randomUUID().toString())
                .header("symbol", UUID.randomUUID().toString())
                .header("side", UUID.randomUUID().toString())
                .header("quantity", String.valueOf(TestUtil.generateRandomInteger()))
                .header("type", UUID.randomUUID().toString())
                .header("timestamp", UUID.randomUUID().toString())
                .header("signature", UUID.randomUUID().toString())
                .POST(HttpRequest.BodyPublishers.ofString(""))
                .build();

        return client.send(request, ofString());
    }
}

package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.factory.CredentialFactory;
import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.SymbolType;
import com.rkd.binance.util.RequestUtil;
import com.rkd.binance.util.SignatureUtil;
import com.rkd.binance.util.TestUtil;
import config.TestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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

import static java.net.http.HttpClient.newHttpClient;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@Import(TestConfig.class)
@ExtendWith({SpringExtension.class, MockitoExtension.class})
public class TradeSpotStrategyTest {

    @InjectMocks
    private TradeSpotStrategy tradeSpotStrategy;

    @Mock
    private BinanceSpotClient binanceSpotClient;

    @Mock
    private LastPriceCandlestickStrategy lastPriceCandlestickStrategy;

    private MockedStatic<SignatureUtil> signatureUtilMockedStatic;

    private MockedStatic<RequestUtil> requestUtilMockedStatic;

    private MockedStatic<CredentialFactory> credentialFactoryMockedStatic;

    @BeforeEach
    public void setUp() {
        signatureUtilMockedStatic = Mockito.mockStatic(SignatureUtil.class);
        requestUtilMockedStatic = Mockito.mockStatic(RequestUtil.class);
        credentialFactoryMockedStatic = Mockito.mockStatic(CredentialFactory.class);
    }

    @AfterEach
    public void tearDown() {
        if (signatureUtilMockedStatic != null) {
            signatureUtilMockedStatic.close();
        }

        if (requestUtilMockedStatic != null) {
            requestUtilMockedStatic.close();
        }

        if (credentialFactoryMockedStatic != null) {
            credentialFactoryMockedStatic.close();
        }
    }

    @Test
    public void tradeSpot() throws IOException, InterruptedException {

        var symbol = SymbolType.getRandomSymbol().getSymbol();
        var money = TestUtil.generateRandomMoney();
        var decision = DecisionType.getRandomDecision().name();
        var lastPrice = TestUtil.generateRandomPrice();

        when(lastPriceCandlestickStrategy.getLastPrice(eq(symbol), anyString())).thenReturn(lastPrice);
        when(binanceSpotClient.tradeSpot(anyString(), anyString(), eq(symbol), eq(decision),
                anyString(), anyString(), anyString(), anyString())).thenReturn(callTradeSpotMocked().body());

        signatureUtilMockedStatic.when(() -> SignatureUtil.getSignature(data, key)).thenReturn(expectedSignature);


        tradeSpotStrategy.tradeSpot(symbol, money, decision);

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

        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

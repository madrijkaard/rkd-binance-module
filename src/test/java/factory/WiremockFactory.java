package factory;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.MediaType;
import stub.kline.BTCKlineStub;
import stub.kline.ETHKlineStub;

import java.io.IOException;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.nio.file.Files.readString;

public class WiremockFactory {

    private WireMockServer wireMockServer = new WireMockServer(0);

    private WiremockFactory() {
        setUp();
    }

    private static class WiremockFactoryHelper {
        private static final WiremockFactory INSTANCE = new WiremockFactory();
    }

    public static WiremockFactory getInstance() {
        return WiremockFactory.WiremockFactoryHelper.INSTANCE;
    }

    private void setUp() {
        wireMockServer.start();
        configureStubs();
    }

    public void tearDown() {
        wireMockServer.stop();
    }

    public WireMockServer getWireMockServer() {
        return wireMockServer;
    }

    private void configureStubs() {

        try {

            BTCKlineStub.configure(wireMockServer);
            ETHKlineStub.configure(wireMockServer);

            String postApiV3OrderResponse = readString(Paths.get("src/test/resources/json/post_api_v3_order_response.json"));

            wireMockServer.stubFor(post(urlEqualTo("/api/v3/order"))
                    .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_VALUE))
                    .withHeader("X-MBX-APIKEY", matching(".*"))
                    .withHeader("symbol", matching(".*"))
                    .withHeader("side", matching(".*"))
                    .withHeader("quantity", matching(".*"))
                    .withHeader("type", matching(".*"))
                    .withHeader("timestamp", matching(".*"))
                    .withHeader("signature", matching(".*"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(postApiV3OrderResponse)));

            String postApiV3AssetResponse = readString(Paths.get("src/test/resources/json/post_api_v3_asset_response.json"));

            wireMockServer.stubFor(post(urlPathEqualTo("/sapi/v3/asset/getUserAsset"))
                    .withHeader("Content-Type", equalTo(MediaType.APPLICATION_JSON_VALUE))
                    .withHeader("X-MBX-APIKEY", matching(".*"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody(postApiV3AssetResponse)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

package factory;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.MediaType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class WiremockFactory {

    private WireMockServer wireMockServer = new WireMockServer(8080);

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

    private void configureStubs() {

        try {

            String jsonResponse = Files.readString(Paths.get("src/test/resources/json/post_api_v3_order_response.json"));

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
                            .withBody(jsonResponse)));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

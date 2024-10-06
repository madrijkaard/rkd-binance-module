package stub.kline;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ETHKlineStub {

    private static String hour1Limit;
    private static String hour50Limit;
    private static String hour100Limit;
    private static String hour200Limit;

    private static String day1Limit;
    private static String day50Limit;
    private static String day100Limit;
    private static String day200Limit;

    private static String week1Limit;
    private static String week50Limit;
    private static String week100Limit;
    private static String week200Limit;

    public static void configure(WireMockServer wireMockServer) throws IOException {

        hour1Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1h_1_limit_response.json"));
        hour50Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1h_50_limit_response.json"));
        hour100Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1h_100_limit_response.json"));
        hour200Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1h_200_limit_response.json"));

        day1Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1d_1_limit_response.json"));
        day50Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1d_50_limit_response.json"));
        day100Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1d_100_limit_response.json"));
        day200Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1d_200_limit_response.json"));

        week1Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1w_1_limit_response.json"));
        week50Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1w_50_limit_response.json"));
        week100Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1w_100_limit_response.json"));
        week200Limit = Files.readString(Paths.get("src/test/resources/json/eth/eth_usdt_1w_200_limit_response.json"));

        // --------------------- interval: 1h --------------------- //

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1h"))
                .withQueryParam("limit", equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(hour1Limit)
                ));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1h"))
                .withQueryParam("limit", equalTo("50"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(hour50Limit)
                ));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1h"))
                .withQueryParam("limit", equalTo("100"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(hour100Limit)
                ));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1h"))
                .withQueryParam("limit", equalTo("200"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(hour200Limit)
                ));

        // --------------------- interval: 1d --------------------- //

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1d"))
                .withQueryParam("limit", equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(day1Limit)
                ));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1d"))
                .withQueryParam("limit", equalTo("50"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(day50Limit)
                ));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1d"))
                .withQueryParam("limit", equalTo("100"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(day100Limit)
                ));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1d"))
                .withQueryParam("limit", equalTo("200"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(day200Limit)
                ));

        // --------------------- interval: 1w --------------------- //

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1w"))
                .withQueryParam("limit", equalTo("1"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(week1Limit)
                ));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1w"))
                .withQueryParam("limit", equalTo("50"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(week50Limit)
                ));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1w"))
                .withQueryParam("limit", equalTo("100"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(week100Limit)
                ));

        wireMockServer.stubFor(get(urlPathEqualTo("/api/v3/uiKlines"))
                .withQueryParam("symbol", matching(".*"))
                .withQueryParam("interval", equalTo("1w"))
                .withQueryParam("limit", equalTo("200"))
                .willReturn(aResponse()
                        .withStatus(HttpStatus.OK.value())
                        .withHeader("Content-Type", "application/json")
                        .withBody(week200Limit)
                ));
    }
}

package config;

import factory.WiremockFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

public class DynamicPropertyConfig {

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        var port = WiremockFactory.getInstance().getWireMockServer().port();
        registry.add("binance.url.spot", () -> "http://localhost:" + port);
    }
}

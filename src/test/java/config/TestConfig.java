package config;

import factory.WiremockFactory;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public WiremockFactory wiremock() {
        return WiremockFactory.getInstance();
    }

    @PreDestroy
    public void cleanUp() {
        WiremockFactory.getInstance().tearDown();
    }
}

package config;

import com.rkd.binance.client.BinanceSpotClient;
import factory.WiremockFactory;
import fake.CredentialComponentFake;
import jakarta.annotation.PreDestroy;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
@EnableFeignClients(clients = BinanceSpotClient.class)
@ImportAutoConfiguration({FeignAutoConfiguration.class, JacksonAutoConfiguration.class, HttpMessageConvertersAutoConfiguration.class})
public class TestConfig {

    @Bean
    public CredentialComponentFake credentialComponentFake() {
        return new CredentialComponentFake();
    }

    @Bean
    public WiremockFactory wiremock() {
        return WiremockFactory.getInstance();
    }

    @PreDestroy
    public void cleanUp() {
        WiremockFactory.getInstance().tearDown();
    }
}

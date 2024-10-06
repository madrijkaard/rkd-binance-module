package com.rkd.binance.component;

import org.springframework.stereotype.Component;

/**
 * Factory responsible for providing access credentials to Binance.
 */
@Component
public class CredentialComponent {

    private final String key = System.getenv("BINANCE_API_KEY");
    private final String secret = System.getenv("BINANCE_API_SECRET");

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }
}

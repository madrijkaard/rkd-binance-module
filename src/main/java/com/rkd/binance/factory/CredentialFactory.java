package com.rkd.binance.factory;

/**
 * Factory responsible for providing access credentials to Binance.
 */
public class CredentialFactory {
    private final String key = System.getenv("BINANCE_API_KEY");
    private final String secret = System.getenv("BINANCE_API_SECRET");

    private CredentialFactory() {}

    private static class CredentialHelper {
        private static final CredentialFactory INSTANCE = new CredentialFactory();
    }

    public static CredentialFactory getInstance() {
        return CredentialHelper.INSTANCE;
    }

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }
}

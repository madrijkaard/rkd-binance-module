package com.rkd.binance.type;

import static com.rkd.binance.type.CryptoType.*;

/**
 * Enumerator responsible for defining currency pairs traded on Binance.
 */
public enum SymbolType {

    BTC_USDT("BTCUSDT", BTC, USDT),
    ETH_USDT("ETHUSDT", ETH, USDT),
    ADA_USDT("ADAUSDT", ADA, USDT),
    AVAX_USDT("AVAXUSDT", AVAX, USDT),
    LINK_USDT("LINKUSDT", LINK, USDT),
    DOT_USDT("DOTUSDT", DOT, USDT),
    ATOM_USDT("ATOMUSDT", ATOM, USDT),
    GRT_USDT("GRTUSDT", GRT, USDT),
    FTM_USDT("FTMUSDT", FTM, USDT),
    SAND_USDT("SANDUSDT", SAND, USDT),
    MANA_USDT("MANAUSDT", MANA, USDT),
    ALGO_USDT("ALGOUSDT", ALGO, USDT),
    INJ_USDT("INJUSDT", INJ, USDT),
    JASMY_USDT("JASMYUSDT", JASMY, USDT),
    BNB_USDT("BNBUSDT", BNB, USDT),
    LTC_USDT("LTCUSDT", LTC, USDT),
    NEAR_USDT("NEARUSDT", NEAR, USDT),
    ETH_BTC("ETHBTC", ETH, USDT),
    ADA_BTC("ADABTC", ADA, BTC),
    AVAX_BTC("AVAXBTC", AVAX, BTC),
    LINK_BTC("LINKBTC", LINK, BTC),
    DOT_BTC("DOTBTC", DOT, BTC),
    ATOM_BTC("ATOMBTC", ATOM, BTC),
    GRT_BTC("GRTBTC", GRT, BTC),
    FTM_BTC("FTMBTC", FTM, BTC),
    SAND_BTC("SANDBTC", SAND, BTC),
    MANA_BTC("MANABTC", MANA, BTC),
    ALGO_BTC("ALGOBTC", ALGO, BTC),
    INJ_BTC("INJBTC", INJ, BTC),
    JASMY_BTC("JASMYBTC", JASMY, BTC),
    BNB_BTC("BNBBTC", BNB, BTC),
    LTC_BTC("LTCBTC", LTC, BTC),
    NEAR_BTC("NEARBTC", NEAR, BTC);

    SymbolType(String symbol, CryptoType crypto, CryptoType reference) {
        this.symbol = symbol;
        this.crypto = crypto;
        this.reference = reference;
    }

    private final String symbol;
    private final CryptoType crypto;
    private final CryptoType reference;

    public String getSymbol() {
        return symbol;
    }

    public CryptoType getCrypto() {
        return crypto;
    }

    public CryptoType getReference() {
        return reference;
    }
}

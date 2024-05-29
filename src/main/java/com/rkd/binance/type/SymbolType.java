package com.rkd.binance.type;

import java.util.Arrays;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

/**
 * Enumerator responsible for defining currency pairs traded on Binance.
 */
public enum SymbolType {
    BTC_USDT("BTCUSDT"),
    ETH_USDT("ETHUSDT"),
    ADA_USDT("ADAUSDT"),
    AVAX_USDT("AVAXUSDT"),
    LINK_USDT("LINKUSDT"),
    MATIC_USDT("MATICUSDT"),
    DOT_USDT("DOTUSDT"),
    ATOM_USDT("ATOMUSDT"),
    GRT_USDT("GRTUSDT"),
    FTM_USDT("FTMUSDT"),
    SAND_USDT("SANDUSDT"),
    MANA_USDT("MANAUSDT"),
    ALGO_USDT("ALGOUSDT"),
    INJ_USDT("INJUSDT"),
    ETH_BTC("ETHBTC"),
    ADA_BTC("ADABTC"),
    AVAX_BTC("AVAXBTC"),
    LINK_BTC("LINKBTC"),
    MATIC_BTC("MATICBTC"),
    DOT_BTC("DOTBTC"),
    ATOM_BTC("ATOMBTC"),
    GRT_BTC("GRTBTC"),
    FTM_BTC("FTMBTC"),
    SAND_BTC("SANDBTC"),
    MANA_BTC("MANABTC"),
    ALGO_BTC("ALGOBTC"),
    INJ_BTC("INJBTC");

    SymbolType(String symbol) {
        this.symbol = symbol;
    }

    private String symbol;

    public String getSymbol() {
        return symbol;
    }

    /**
     * Method responsible for validating the enumerator's input data.
     *
     * @param symbol currency pair
     * @return value found in enumerator
     */
    public static SymbolType of(String symbol) {
        return Arrays.stream(SymbolType.values())
                .filter(symbolType -> symbolType.symbol.equalsIgnoreCase(symbol)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("symbol")));
    }
}

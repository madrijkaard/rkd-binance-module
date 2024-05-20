package com.rkd.binance.type;

import java.util.Arrays;
import java.util.Optional;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

/**
 * Enumerator responsible for representing cryptocurrency codes.
 */
public enum CryptoType {
    BTC, // Bitcoin
    ETH, // Ethereum
    ADA, // Cardano
    AVAX, // Avalanche
    LINK, // Chainlink
    MATIC, // Polygon
    DOT, // Polkadot
    ATOM, // Cosmos
    GRT, // The Graph
    FTM, // Fantom
    SAND, // Sandbox
    MANA, // Decentraland
    ALGO, // Algorand
    INJ; // Injective

    /**
     * Method responsible for converting an input of type String into an object of type CryptoType.
     *
     * @param crypto String type input
     * @return object of type CryptoType
     */
    public static CryptoType of(String crypto) {
        return Arrays.stream(CryptoType.values())
                .filter(cryptoType -> cryptoType.name().equalsIgnoreCase(crypto)).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("crypto")));
    }

    /**
     *
     *
     * @param crypto
     * @return
     */
    public static Optional<CryptoType> exist(String crypto) {
        return Arrays.stream(CryptoType.values()).filter(
                cryptoType -> cryptoType.name().equalsIgnoreCase(crypto)).findFirst();
    }
}

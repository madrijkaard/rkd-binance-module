package com.rkd.binance.dto;

import com.rkd.binance.type.CryptoType;

import java.util.HashMap;

public record SpotWalletDto(double fiatCoin,
                            double stableCoin,
                            HashMap<CryptoType, Double> cryptos) {
}

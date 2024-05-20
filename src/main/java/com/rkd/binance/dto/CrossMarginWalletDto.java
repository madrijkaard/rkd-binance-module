package com.rkd.binance.dto;

public record CrossMarginWalletDto(String asset,
                                   double total,
                                   double cross,
                                   double available,
                                   double pnl) {
}

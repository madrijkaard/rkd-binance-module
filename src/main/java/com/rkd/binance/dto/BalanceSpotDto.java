package com.rkd.binance.dto;

/**
 * Class responsible for representing the balance of a wallet.
 *
 * @param asset The symbol of the asset (e.g., "BTC", "ETH").
 * @param free The free balance of the asset. This is the amount available for trading.
 * @param locked The locked balance of the asset. This is the amount reserved for open orders.
 * @param freeze The frozen balance of the asset. This is the amount frozen due to various reasons, such as ongoing withdrawals or account restrictions.
 * @param withdrawing The amount of the asset currently being withdrawn.
 * @param ipoable The amount of the asset available for IPO (Initial Public Offering).
 * @param btcValuation The BTC valuation of the asset. This field shows the value of the asset in BTC if the needBtcValuation parameter is true.
 */
public record BalanceSpotDto(
        String asset,
        String free,
        String locked,
        String freeze,
        String withdrawing,
        String ipoable,
        String btcValuation
) {}

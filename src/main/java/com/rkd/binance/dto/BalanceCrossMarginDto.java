package com.rkd.binance.dto;

/**
 * A record representing the balance information for an asset in a Binance futures account.
 *
 * @param accountAlias A unique alias for the account
 * @param asset The symbol of the asset (e.g., "BTC", "ETH", "USDT")
 * @param balance The total balance of the asset in the futures account
 * @param crossWalletBalance The balance of the asset in the cross margin wallet
 * @param crossUnPnl The unrealized profit and loss of the asset in the cross margin wallet
 * @param availableBalance The available balance of the asset for trading
 * @param maxWithdrawAmount The maximum amount of the asset that can be withdrawn
 * @param marginAvailable Indicates whether margin is available for the asset
 * @param updateTime The timestamp of the last update
 */
public record BalanceCrossMarginDto(
        String accountAlias,
        String asset,
        double balance,
        double crossWalletBalance,
        double crossUnPnl,
        double availableBalance,
        double maxWithdrawAmount,
        boolean marginAvailable,
        long updateTime
) {}

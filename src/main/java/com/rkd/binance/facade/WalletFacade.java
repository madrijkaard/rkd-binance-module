package com.rkd.binance.facade;

import com.rkd.binance.dto.CrossMarginWalletDto;
import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.strategy.BalanceCrossMarginStrategy;
import com.rkd.binance.strategy.BalanceSpotStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Facade responsible for strategies related to spot and futures market.
 */
@Service
public class WalletFacade {

    @Autowired
    private BalanceSpotStrategy balanceSpotStrategy;
    @Autowired
    private BalanceCrossMarginStrategy balanceFutureStrategy;

    /**
     * Method responsible for loading the wallet balance. Upon return, it will be possible to know the current amount of
     * resources in fiat currency, stable currency and cryptocurrencies.
     *
     * @param fiatCoin   BRL
     * @param stableCoin USDT
     * @return wallet containing fiat currency, stable currency and list of cryptocurrencies
     */
    public SpotWalletDto loadSpot(String fiatCoin, String stableCoin) {
        return balanceSpotStrategy.getBalance(fiatCoin, stableCoin);
    }

    /**
     * Method responsible for obtaining the balance of an account and futures markets.
     *
     * @return list containing balance information for various assets
     */
    public List<CrossMarginWalletDto> loadFuture() {
        return balanceFutureStrategy.getBalance();
    }
}

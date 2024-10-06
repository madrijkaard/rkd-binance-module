package com.rkd.binance.facade;

import com.rkd.binance.dto.CrossMarginWalletDto;
import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.strategy.BalanceCrossMarginStrategy;
import com.rkd.binance.strategy.BalanceSpotStrategy;
import com.rkd.binance.strategy.CheckMoneyStrategy;
import com.rkd.binance.type.CryptoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Facade responsible for wallet component strategies in the spot and futures market.
 */
@Service
public class WalletFacade {

    @Autowired
    private BalanceSpotStrategy balanceSpotStrategy;
    @Autowired
    private BalanceCrossMarginStrategy balanceFutureStrategy;
    @Autowired
    private CheckMoneyStrategy checkMoneyStrategy;

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

    /**
     * Method responsible for checking whether the user has the minimum amount to trade.
     *
     * @param cryptoTypes list of cryptocurrencies
     * @return check if the user has money
     */
    public boolean haveMoney(List<CryptoType> cryptoTypes, double stableCoin, double minimumStableCoin) {
        return checkMoneyStrategy.haveMoney(cryptoTypes, stableCoin, minimumStableCoin);
    }
}

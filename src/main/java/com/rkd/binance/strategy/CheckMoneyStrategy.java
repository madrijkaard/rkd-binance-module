package com.rkd.binance.strategy;

import com.rkd.binance.type.CryptoType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class responsible for checking whether the user's available resources allow them to negotiate.
 */
@Service
public class CheckMoneyStrategy {

    /**
     * Method responsible for checking whether the user has the minimum amount to trade.
     *
     * @param cryptoTypes list of cryptocurrencies
     * @return check if the user has money
     */
    public boolean haveMoney(List<CryptoType> cryptoTypes, double stableCoin, double minimumStableCoin) {

        if (cryptoTypes.isEmpty() && stableCoin >= minimumStableCoin)
            return true;

        var quantity = cryptoTypes.size();

        var result = stableCoin / quantity;

        return result >= minimumStableCoin;
    }
}

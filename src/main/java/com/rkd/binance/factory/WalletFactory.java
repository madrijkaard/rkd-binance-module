package com.rkd.binance.factory;

import com.rkd.binance.dto.CrossMarginWalletDto;
import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.facade.WalletFacade;
import com.rkd.binance.type.CryptoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.List;

/**
 * Factory responsible for providing spot and futures market wallet used on Binance.
 */
@Configuration
public class WalletFactory {

    @Value("${binance.wallet.standard-fiat-coin}")
    private String fiatCoin;
    @Value("${binance.wallet.standard-stable-coin}")
    private String stableCoin;
    @Value("${binance.market.minimum-stable-coin}")
    private double minimumStableCoin;
    @Value("${binance.market.minimum-fiat-coin}")
    private double minimumFiatCoin;

    @Autowired
    private WalletFacade walletFacade;

    /**
     * @return
     */
    /*@Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)*/
    public SpotWalletDto spotWallet() {
        var walletDto = walletFacade.loadSpot(fiatCoin, stableCoin);
        return checkMinimumQuantity(walletDto);
    }

    /**
     * @return
     */
    /*@Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)*/
    public List<CrossMarginWalletDto> futureWallet() {
        return walletFacade.loadFuture();
    }

    public String getFiatCoin() {
        return fiatCoin;
    }

    public String getStableCoin() {
        return stableCoin;
    }

    public double getMinimumStableCoin() {
        return minimumStableCoin;
    }

    public double getMinimumFiatCoin() {
        return minimumFiatCoin;
    }

    public boolean haveMoney(List<CryptoType> cryptoTypes) {

        if (cryptoTypes.isEmpty() && spotWallet().stableCoin() >= minimumStableCoin)
            return true;

        var quantity = cryptoTypes.size();

        var result = spotWallet().stableCoin() / quantity;

        return result >= minimumStableCoin;
    }

    public SpotWalletDto checkMinimumQuantity(SpotWalletDto spotWalletDto) {

        HashMap<CryptoType, Double> cryptos = new HashMap<>();

        spotWalletDto.cryptos().forEach(
                (cryptoType, money) -> {
                    if (money >= 20) {
                        cryptos.put(cryptoType, money);
                    }
                });

        return new SpotWalletDto(spotWalletDto.fiatCoin(), spotWalletDto.stableCoin(), cryptos);
    }
}

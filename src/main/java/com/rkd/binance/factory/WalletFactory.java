package com.rkd.binance.factory;

import com.rkd.binance.dto.CrossMarginWalletDto;
import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.facade.WalletFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

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

    @Autowired
    private WalletFacade walletFacade;

    /**
     * @return
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public SpotWalletDto spotWallet() {
        return walletFacade.loadSpot(fiatCoin, stableCoin);
    }

    /**
     * @return
     */
    @Bean
    @Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
    public List<CrossMarginWalletDto> futureWallet() {
        return walletFacade.loadFuture();
    }

    public String getFiatCoin() {
        return fiatCoin;
    }

    public String getStableCoin() {
        return stableCoin;
    }
}

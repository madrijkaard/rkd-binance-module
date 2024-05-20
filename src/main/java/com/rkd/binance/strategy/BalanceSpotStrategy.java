package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.dto.BalanceSpotDto;
import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.factory.CredentialFactory;
import com.rkd.binance.type.CryptoType;
import com.rkd.binance.util.RequestUtil;
import com.rkd.binance.util.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Class responsible for wallet balance functionality.
 */
@Service
public class BalanceSpotStrategy {

    @Autowired
    private BinanceSpotClient binanceClient;

    /**
     * Method responsible for loading the wallet balance. Upon return, it will be possible to know the current amount of
     * resources in fiat currency, stable currency and cryptocurrencies.
     *
     * @param fiatCoin BRL
     * @param stableCoin USDT
     * @return wallet containing fiat currency, stable currency and list of cryptocurrencies
     */
    public SpotWalletDto getBalance(String fiatCoin, String stableCoin) {

        var balanceDtoList = getBalance();

        var fiatCoinLoaded = loadFiat(fiatCoin, balanceDtoList);
        var stableCoinLoaded = loadStable(stableCoin, balanceDtoList);
        var cryptosLoaded = loadCryptos(balanceDtoList);

        return new SpotWalletDto(fiatCoinLoaded, stableCoinLoaded, cryptosLoaded);
    }

    /**
     * Method responsible for obtaining the balance of an asset in the wallet.
     *
     * @return a list of balance details for each asset in the wallet
     */
    public List<BalanceSpotDto> getBalance() {

        long milliseconds = System.currentTimeMillis();

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("timestamp", String.valueOf(milliseconds));

        var queryPath = RequestUtil.joinQueryParameters(parameters);
        var signature = SignatureUtil.getSignature(queryPath, CredentialFactory.getInstance().getSecret());

        return binanceClient.getBalanceSpot(APPLICATION_JSON_VALUE,
                CredentialFactory.getInstance().getKey(), String.valueOf(milliseconds), signature);
    }

    /**
     * Method responsible for loading the balance of a specified fiat currency.
     *
     * @param fiatCoin the fiat currency to be loaded (e.g., BRL)
     * @param balanceDtoList the list of balances from the wallet
     * @return the balance amount of the specified fiat currency
     * @throws IllegalStateException if the fiat currency is not defined
     */
    private double loadFiat(String fiatCoin, List<BalanceSpotDto> balanceDtoList) {

        if (fiatCoin == null || fiatCoin.isEmpty() || fiatCoin.isBlank()) {
            throw new IllegalStateException("Fiat coin has not been defined");
        }

        return balanceDtoList.stream().filter(
                        balanceDto -> balanceDto.asset().equalsIgnoreCase(fiatCoin))
                .findFirst().map(BalanceSpotDto::free).map(Double::parseDouble).orElse(0D);
    }

    /**
     * Method responsible for loading the balance of a specified stable currency.
     *
     * @param stableCoin the stable currency to be loaded (e.g., USDT)
     * @param balanceDtoList the list of balances from the wallet
     * @return the balance amount of the specified stable currency
     * @throws IllegalStateException if the stable currency is not defined
     */
    private double loadStable(String stableCoin, List<BalanceSpotDto> balanceDtoList) {

        if (stableCoin == null || stableCoin.isEmpty() || stableCoin.isBlank()) {
            throw new IllegalStateException("Stable coin has not been defined");
        }

        return balanceDtoList.stream().filter(
                        balanceDto -> balanceDto.asset().equalsIgnoreCase(stableCoin))
                .findFirst().map(BalanceSpotDto::free).map(Double::parseDouble).orElse(0D);
    }

    /**
     * Method responsible for loading the balances of various cryptocurrencies.
     *
     * @param balanceDtoList the list of balances from the wallet
     * @return a map containing the types of cryptocurrencies and their respective balances
     */
    private HashMap<CryptoType, Double> loadCryptos(List<BalanceSpotDto> balanceDtoList) {

        HashMap<CryptoType, Double> cryptos = new HashMap<>();

        balanceDtoList.forEach(
                balanceDto -> CryptoType.exist(balanceDto.asset()).ifPresent(
                        cryptoType -> cryptos.put(cryptoType, Double.valueOf(balanceDto.free()))));

        return cryptos;
    }
}

package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.component.CredentialComponent;
import com.rkd.binance.dto.BalanceSpotDto;
import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.type.CryptoType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static com.rkd.binance.definition.BinanceDefinition.MINIMUM_AMOUNT_OF_MONEY_IN_USDT;
import static com.rkd.binance.util.EnumUtil.*;
import static com.rkd.binance.util.RequestUtil.joinQueryParameters;
import static com.rkd.binance.util.SignatureUtil.getSignature;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Class responsible for wallet balance functionality.
 */
@Service
public class BalanceSpotStrategy {

    private CredentialComponent credentialComponent;
    private BinanceSpotClient binanceClient;
    private LastPriceCandlestickStrategy lastPriceCandlestickStrategy;

    @Autowired
    public BalanceSpotStrategy(CredentialComponent credentialComponent, BinanceSpotClient binanceClient) {
        this.credentialComponent = credentialComponent;
        this.binanceClient = binanceClient;
    }

    /**
     * Method responsible for loading the wallet balance. Upon return, it will be possible to know the current amount of
     * resources in fiat currency, stable currency and cryptocurrencies.
     *
     * @param fiatCoin   fiat currency (e.g., BRL)
     * @param stableCoin stable coin (e.g., USDT)
     * @return wallet containing fiat currency, stable currency and list of cryptocurrencies
     */
    public SpotWalletDto getBalance(String fiatCoin, String stableCoin) {

        var balanceDtoList = getBalance();
        var fiatCoinLoaded = loadFiat(fiatCoin, balanceDtoList);
        var stableCoinLoaded = loadStable(stableCoin, balanceDtoList);
        var cryptosLoaded = loadCryptos(balanceDtoList);
        var spotWalletDto = new SpotWalletDto(fiatCoinLoaded, stableCoinLoaded, cryptosLoaded);

        return checkMinimumQuantity(spotWalletDto);
    }

    /**
     * Method responsible for obtaining the wallet balance on Binance.
     *
     * @return detailed list containing all the assets in a wallet on Binance
     */
    private List<BalanceSpotDto> getBalance() {

        long milliseconds = System.currentTimeMillis();

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("timestamp", String.valueOf(milliseconds));

        var queryPath = joinQueryParameters(parameters);
        var secret = credentialComponent.getSecret();
        var key = credentialComponent.getKey();
        var signature = getSignature(queryPath, secret);

        return binanceClient.getBalanceSpot(APPLICATION_JSON_VALUE, key, String.valueOf(milliseconds), signature);
    }

    /**
     * Method responsible for loading the balance of a specified fiat currency.
     *
     * @param fiatCoin       the fiat currency to be loaded (e.g., BRL)
     * @param balanceDtoList detailed list containing all the assets in a wallet on Binance
     * @return the balance amount of the specified fiat currency
     * @throws IllegalStateException if the fiat currency is not defined
     */
    private double loadFiat(String fiatCoin, List<BalanceSpotDto> balanceDtoList) {

        if (fiatCoin == null || fiatCoin.isEmpty() || fiatCoin.isBlank()) {
            throw new IllegalStateException("Fiat coin has not been defined");
        }

        return balanceDtoList.stream().filter(balanceDto -> balanceDto.asset().equalsIgnoreCase(fiatCoin))
                .findFirst().map(BalanceSpotDto::free).map(Double::parseDouble).orElse(0D);
    }

    /**
     * Method responsible for loading the balance of a specified stable currency.
     *
     * @param stableCoin     the stable currency to be loaded (e.g., USDT)
     * @param balanceDtoList detailed list containing all the assets in a wallet on Binance
     * @return the balance amount of the specified stable currency
     * @throws IllegalStateException if the stable currency is not defined
     */
    private double loadStable(String stableCoin, List<BalanceSpotDto> balanceDtoList) {

        if (stableCoin == null || stableCoin.isEmpty() || stableCoin.isBlank()) {
            throw new IllegalStateException("Stable coin has not been defined");
        }

        return balanceDtoList.stream().filter(balanceDto -> balanceDto.asset().equalsIgnoreCase(stableCoin))
                .findFirst().map(BalanceSpotDto::free).map(Double::parseDouble).orElse(0D);
    }

    /**
     * Method responsible for loading the balances of various cryptocurrencies.
     *
     * @param balanceDtoList detailed list containing all the assets in a wallet on Binance
     * @return map containing the types of cryptocurrencies and their respective balances
     */
    private HashMap<CryptoType, Double> loadCryptos(List<BalanceSpotDto> balanceDtoList) {

        HashMap<CryptoType, Double> cryptos = new HashMap<>();

        balanceDtoList.forEach(
                balanceDto -> exist(CryptoType.class, balanceDto.asset())
                        .ifPresent(cryptoType -> cryptos.put(cryptoType, Double.valueOf(balanceDto.free()))));

        return cryptos;
    }

    /**
     * Method responsible for checking which cryptos have the minimum balance to be eligible for trading.
     *
     * @param spotWalletDto wallet containing fiat currency, stable currency and list of cryptocurrencies
     * @return wallet containing assets with a valid balance for trading
     */
    private SpotWalletDto checkMinimumQuantity(SpotWalletDto spotWalletDto) {

        HashMap<CryptoType, Double> cryptos = new HashMap<>();

        spotWalletDto.cryptos().forEach(
                (cryptoType, money) -> {
                    if (MINIMUM_AMOUNT_OF_MONEY_IN_USDT <= money) {
                        cryptos.put(cryptoType, money);
                    }
                });

        return new SpotWalletDto(spotWalletDto.fiatCoin(), spotWalletDto.stableCoin(), cryptos);
    }

    /*private boolean isValid(CryptoType cryptoType, Double money, String stableCoin) {
        var lastPrice = lastPriceCandlestickStrategy.getLastPrice();
    }*/
}

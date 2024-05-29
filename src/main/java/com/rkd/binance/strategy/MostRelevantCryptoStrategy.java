package com.rkd.binance.strategy;

import com.rkd.binance.type.CryptoType;
import com.rkd.binance.type.IntervalType;
import com.rkd.binance.util.MovingAverageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 */
@Service
public class MostRelevantCryptoStrategy {

    private static final String ONE_DAY_INTERVAL = IntervalType.ONE_DAY.getInterval();
    private static final int ONE_LIMIT = 1;

    @Autowired
    private LoadMarketStrategy loadMarketStrategy;

    /**
     * method responsible for returning the cryptocurrencies that increased in value in 1 day.
     *
     * @param mostRelevant amount of cryptocurrencies that will be returned
     * @return list of cryptocurrencies
     */
    public List<CryptoType> rankMostRelevant(int rank) {

        HashMap<CryptoType, Double> cryptos = new HashMap<>();

        Arrays.stream(CryptoType.values()).forEach(cryptoType -> {

            if (cryptoType.equals(CryptoType.BTC)) {
                return;
            }

            var symbol = cryptoType.name() + CryptoType.BTC.name();

            var klineModel = loadMarketStrategy.load(symbol, ONE_DAY_INTERVAL, ONE_LIMIT).stream().findFirst().orElseThrow(IllegalStateException::new);

            var amplitude = MovingAverageUtil.calculateAmplitude(Double.parseDouble(klineModel.getOpenPrice()), Double.parseDouble(klineModel.getClosePrice()));

            if (Double.parseDouble(klineModel.getOpenPrice()) > Double.parseDouble(klineModel.getClosePrice())) {
                amplitude = -amplitude;
            }

            cryptos.put(cryptoType, amplitude);
        });

        return getTopCryptos(cryptos, rank);
    }

    /**
     * method responsible for returning the cryptocurrencies that appreciated the most.
     *
     * @param cryptos      cryptocurrency and its appreciation percentage
     * @param mostRelevant amount of cryptocurrencies that will be returned
     * @return list of cryptocurrencies
     */
    private List<CryptoType> getTopCryptos(HashMap<CryptoType, Double> cryptos, int mostRelevant) {
        return cryptos.entrySet()
                .stream()
                .sorted(Map.Entry.<CryptoType, Double>comparingByValue().reversed())
                .limit(mostRelevant)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}

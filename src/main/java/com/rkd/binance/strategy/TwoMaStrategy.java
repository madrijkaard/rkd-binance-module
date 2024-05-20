package com.rkd.binance.strategy;

import com.rkd.binance.model.KlineModel;
import com.rkd.binance.type.DecisionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static com.rkd.binance.definition.ExceptionDefinition.*;
import static com.rkd.binance.type.DecisionType.*;

/**
 * Class responsible for executing the buying and selling strategy using two moving averages.
 */
@Service
public class TwoMaStrategy {

    @Autowired
    private LoadMarketStrategy loadMarketStrategy;

    /**
     * Method responsible for executing the strategy using two moving averages.
     *
     * @param symbol   currency pair
     * @param intervals chart period
     * @param ma       number of candlesticks that will return
     * @return type of decision: buy, sell and wait
     */
    public DecisionType execute(String symbol, List<String> intervals, int... ma) {
        return prepare(symbol, intervals, ma);
    }

    /**
     * Method responsible for preparing the data that will be used to calculate the moving averages.
     *
     * @param symbol   currency pair
     * @param intervals chart period
     * @param ma       number of candlesticks that will return
     * @return type of decision: buy, sell and wait
     */
    private DecisionType prepare(String symbol, List<String> intervals, int... ma) {

        if (intervals.size() != 1 || ma.length != 2) {
            throw new IllegalArgumentException();
        }

        String interval = intervals.stream().findFirst().orElseThrow(IllegalStateException::new);

        int sma = Arrays.stream(ma).min().getAsInt();
        int lma = Arrays.stream(ma).max().getAsInt();

        CompletableFuture<Double> completableFutureSma = CompletableFuture.supplyAsync(() -> getCandlesticksAndCalculateMa(symbol, interval, sma));
        CompletableFuture<Double> completableFutureLma = CompletableFuture.supplyAsync(() -> getCandlesticksAndCalculateMa(symbol, interval, lma));

        CompletableFuture<DecisionType> combinedFuture = completableFutureSma.thenCombine(completableFutureLma, this::decide);

        try {
            return combinedFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new IllegalStateException(ERROR_IN_CALCULATING_MOVING_AVERAGES);
        }
    }

    /**
     * Method responsible for calculating the long moving average.
     *
     * @param symbol   currency pair
     * @param interval chart period
     * @param quantity candlesticks that will return
     * @return long moving average
     */
    private double getCandlesticksAndCalculateMa(String symbol, String interval, int quantity) {

        if (quantity < 1) {
            throw new IllegalArgumentException();
        }

        var klineModelList = loadMarketStrategy.load(symbol, interval, quantity);

        double[] closingPrices = klineModelList
                .stream()
                .map(KlineModel::getClosePrice)
                .filter(Objects::nonNull)
                .mapToDouble(Double::parseDouble)
                .toArray();

        if (closingPrices.length != quantity) {
            throw new IllegalArgumentException(INVALID_MOVING_AVERAGE);
        }

        return calculateMa(closingPrices);
    }

    /**
     * Method responsible for calculating a simple moving average.
     *
     * @param closingPrices closing prices list
     * @return moving average
     * @throws IllegalArgumentException price list is empty
     */
    private double calculateMa(double[] closingPrices) {

        if (closingPrices == null || closingPrices.length == 0) {
            throw new IllegalArgumentException(PRICE_LIST_IS_EMPTY);
        }

        int period = closingPrices.length;
        double sum = 0.0;

        for (double valor : closingPrices) {
            sum += valor;
        }

        return sum / period;
    }

    /**
     * Method responsible for deciding what to do in the operation.
     *
     * @param sma short moving average
     * @param lma long moving average
     * @return type of decision: buy, sell or wait
     */
    private DecisionType decide(double sma, double lma) {

        if (sma == lma) {
            return WAIT;
        }

        return sma > lma ? BUY : SELL;
    }
}

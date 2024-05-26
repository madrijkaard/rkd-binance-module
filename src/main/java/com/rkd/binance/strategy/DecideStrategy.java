package com.rkd.binance.strategy;

import com.rkd.binance.type.DecisionType;
import com.rkd.binance.type.VectorType;
import com.rkd.binance.util.MovingAverageUtil;
import org.springframework.stereotype.Service;

import static com.rkd.binance.type.DecisionType.*;
import static com.rkd.binance.type.DecisionType.SELL;

/**
 * Class responsible for the trading decision during the execution of a strategy.
 */
@Service
public class DecideStrategy {

    /**
     * Method responsible for deciding what to do in the operation.
     *
     * @param sma short moving average
     * @param lma long moving average
     * @param vectorType direction that will be used to execute the gains, example: UP or DOWN
     * @param minimumRange minimum required to execute a trade
     * @param maximumRange maximum required to execute a trade
     * @return BUY, CROSS_BUY, SELL, CROSS_SELL, WAIT
     */
    public DecisionType decide(double sma, double lma, VectorType vectorType, float minimumRange, float maximumRange) {

        if (sma == lma) {
            return WAIT;
        }

        var amplitude = MovingAverageUtil.calculateAmplitude(sma, lma);

        if (sma > lma) {
            return checkCrossBuy(amplitude, vectorType, minimumRange, maximumRange);
        } else {
            return checkCrossSell(amplitude, vectorType, minimumRange, maximumRange);
        }
    }

    /**
     * Method responsible for verifying a cross-purchase.
     *
     * @param amplitude range between two prices
     * @param vectorType direction that will be used to execute the gains, example: UP or DOWN
     * @param minimumRange minimum required to execute a trade
     * @param maximumRange maximum required to execute a trade
     * @return BUY, CROSS_BUY, SELL, CROSS_SELL, WAIT
     */
    private DecisionType checkCrossBuy(double amplitude, VectorType vectorType, float minimumRange, float maximumRange) {
        if (amplitude >= minimumRange && amplitude <= maximumRange) {
            return VectorType.isUp(vectorType) ? CROSS_BUY : BUY;
        }
        return BUY;
    }

    /**
     * Method responsible for verifying a cross-sale.
     *
     * @param amplitude range between two prices
     * @param vectorType direction that will be used to execute the gains, example: UP or DOWN
     * @param minimumRange minimum required to execute a trade
     * @param maximumRange maximum required to execute a trade
     * @return BUY, CROSS_BUY, SELL, CROSS_SELL, WAIT
     */
    private DecisionType checkCrossSell(double amplitude, VectorType vectorType, float minimumRange, float maximumRange) {
        if (amplitude >= minimumRange && amplitude <= maximumRange) {
            return VectorType.isDown(vectorType) ? CROSS_SELL : SELL;
        }
        return SELL;
    }
}

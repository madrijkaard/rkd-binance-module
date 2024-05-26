package com.rkd.binance.strategy;

import com.rkd.binance.type.DecisionType;
import com.rkd.binance.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockitoExtension.class)
public class DecideStrategyTest {

    @InjectMocks
    private DecideStrategy decideStrategy;

    @Test
    public void decide() {

        var prices = TestUtil.generateTwoRandomDoubles();
        var ranges = TestUtil.generateTwoRandomFloats();

        var sma = prices[0];
        var lma = prices[1];

        var vector = TestUtil.getRandomVectorType();

        var minimum = ranges[0];
        var maximum = ranges[1];

        var result = decideStrategy.decide(sma, lma, vector, minimum, maximum);

        if (sma < lma) {
            assertTrue(result == DecisionType.SELL || result == DecisionType.CROSS_SELL);
        }

        if (sma > lma) {
            assertTrue(result == DecisionType.BUY || result == DecisionType.CROSS_BUY);
        }
    }
}

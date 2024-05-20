package com.rkd.binance.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Aspect
@Component
public class MarketAspect {

    Logger logger = LoggerFactory.getLogger(MarketAspect.class);

    @Pointcut("execution(* com.rkd.binance.facade.StrategyFacade.execute(..))")
    public void executeMethod() {
    }

    @Before("executeMethod() && args(symbol, intervals, ma)")
    public void logMethodEntry(String symbol, List<String> intervals, int... ma) {
        logger.info("------------------ TwoMaStrategy ------------------");
        logger.info("symbol: " + symbol);
        logger.info("intervals: " + intervals);
        logger.info("quantitySma: " + Arrays.toString(ma));
    }

    @AfterReturning(pointcut = "executeMethod()", returning = "result")
    public void logMethodExit(Object result) {
        logger.info("Decision: " + result);
    }
}

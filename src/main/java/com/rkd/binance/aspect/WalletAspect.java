package com.rkd.binance.aspect;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Aspect for logging execution of service methods in WalletFacade.
 */
@Aspect
@Component
public class WalletAspect {

    Logger logger = LoggerFactory.getLogger(WalletAspect.class);

    /**
     * Pointcut that matches the loadSpot method execution in WalletFacade.
     */
    @Pointcut("execution(* com.rkd.binance.facade.WalletFacade.loadSpot(..))")
    public void executeSpotMethod() {
    }

    /**
     * Pointcut that matches the loadFuture method execution in WalletFacade.
     */
    @Pointcut("execution(* com.rkd.binance.facade.WalletFacade.loadFuture(..))")
    public void executeFutureMethod() {
    }

    /**
     * Advice that logs the method entry for loadSpot.
     *
     * @param fiatCoin The fiat currency used in the loadSpot method
     * @param stableCoin The stablecoin used in the loadSpot method
     */
    @Before("executeSpotMethod() && args(fiatCoin, stableCoin)")
    public void logSpotMethodEntry(String fiatCoin, String stableCoin) {
        logger.info("------------------ Spot Account Balance ------------------");
        logger.info("fiatCoin: {}", fiatCoin);
        logger.info("stableCoin: {}", stableCoin);
    }

    /**
     * Advice that logs the method exit for loadSpot.
     *
     * @param result The result returned by the loadSpot method
     */
    @AfterReturning(pointcut = "executeSpotMethod()", returning = "result")
    public void logSpotMethodExit(Object result) {
        logger.info("Balance spot: " + result);
    }

    /**
     * Advice that logs the method entry for loadFuture.
     */
    @Before("executeFutureMethod()")
    public void logFutureMethodEntry() {
        logger.info("------------------ Future Account Balance ------------------");
    }

    /**
     * Advice that logs the method exit for loadFuture.
     *
     * @param result The result returned by the loadFuture method.
     */
    @AfterReturning(pointcut = "executeFutureMethod()", returning = "result")
    public void logFutureMethodExit(Object result) {
        logger.info("Balance future: {}", result);
    }
}

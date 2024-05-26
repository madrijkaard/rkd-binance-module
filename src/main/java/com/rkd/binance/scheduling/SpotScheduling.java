package com.rkd.binance.scheduling;

import com.rkd.binance.executor.BinanceExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduling responsible for starting the module trading process with Binance.
 */
@Component
public class SpotScheduling extends BinanceExecutor {

    /**
     * Method responsible for executing the scheduler according to the period defined in the module's main properties file.
     */
    @Scheduled(fixedRateString = "${module.spot.frequency}")
    public void test() {
        execute();
    }
}

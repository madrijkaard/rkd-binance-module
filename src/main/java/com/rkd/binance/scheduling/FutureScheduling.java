package com.rkd.binance.scheduling;

import com.rkd.binance.executor.BinanceExecutor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FutureScheduling extends BinanceExecutor {
    @Scheduled(fixedRateString = "${module.future.frequency}")
    public void test() {
    }
}

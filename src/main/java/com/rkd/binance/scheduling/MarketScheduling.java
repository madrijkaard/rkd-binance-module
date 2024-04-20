package com.rkd.binance.scheduling;

import com.rkd.binance.service.KlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Class responsible for periodically seeking market information.
 */
@Service
public class MarketScheduling {

    @Autowired
    private KlineService klineService;

    /**
     * Method responsible for seeking updated market information.
     */
    @Scheduled(fixedRate = 10000)
    public void listKlines() {
        klineService.listKlines();
    }
}

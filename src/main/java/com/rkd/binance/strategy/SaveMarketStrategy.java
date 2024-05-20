package com.rkd.binance.strategy;

import com.rkd.binance.model.KlineModel;
import com.rkd.binance.repository.KlineRepository;
import com.rkd.binance.type.IntervalType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import static com.rkd.binance.definition.ExceptionDefinition.CANDLESTICK_LIST_IS_EMPTY;

/**
 * Class responsible for the strategy of updating the latest market information.
 */
@Service
public class SaveMarketStrategy {

    @Autowired
    private KlineRepository klineRepository;

    /**
     * Method responsible for saving market information in the database.
     *
     * @param klineList list of candlesticks
     */
    public void saveMarket(List<KlineModel> klineModelList) {

        var klineSequence =
                klineModelList.stream().findFirst().orElseThrow(
                        () -> new IllegalStateException(CANDLESTICK_LIST_IS_EMPTY));

        var intervalByTimestamp =
                IntervalType.getIntervalByTimestamp(
                        klineSequence.getOpenTime().toEpochMilli(), klineSequence.getCloseTime().toEpochMilli());

        Optional<Integer> sequence =
                klineRepository.findMaxSequenceByOpenTimeAndCloseTime(
                        klineSequence.getOpenTime(), klineSequence.getCloseTime());

        AtomicInteger atomicInteger = new AtomicInteger(sequence.orElse(0));

        klineModelList.forEach(
                klineModel -> {
                    klineModel.setInterval(intervalByTimestamp.getInterval());
                    klineModel.setSequence(atomicInteger.incrementAndGet());
                });

        klineRepository.saveAll(klineModelList);
    }
}

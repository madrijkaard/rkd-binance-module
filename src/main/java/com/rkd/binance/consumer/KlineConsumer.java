package com.rkd.binance.consumer;

import com.rkd.binance.converter.KlineConverter;
import com.rkd.binance.repository.KlineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.rkd.binance.definition.ExceptionDefinition.ERROR_DURING_CONVERSION;
import static com.rkd.binance.definition.GroupDefinition.KLINE_CASSANDRA_GROUP;
import static com.rkd.binance.definition.TopicDefinition.KLINE_TOPIC;

@Component
public class KlineConsumer {
    @Autowired
    private KlineConverter klineConverter;
    @Autowired
    private KlineRepository klineRepository;

    @KafkaListener(topics = KLINE_TOPIC, groupId = KLINE_CASSANDRA_GROUP)
    public void receiveMessage(String klineListAsString) {

        List<List<String>> klineList = parseStringToList(klineListAsString);

        var klineModelList = klineConverter.convertOf(klineList);

        var klineSequence = klineModelList.stream().findFirst().orElseThrow(() -> new IllegalStateException(ERROR_DURING_CONVERSION.concat("kline")));

        Optional<Integer> sequence = klineRepository.findMaxSequenceByOpenTimeAndCloseTime(klineSequence.getOpenTime(), klineSequence.getCloseTime());

        AtomicInteger atomicInteger = new AtomicInteger(sequence.orElse(0));

        klineModelList.forEach(klineModel -> klineModel.setSequence(atomicInteger.incrementAndGet()));

        klineRepository.saveAll(klineModelList);
    }

    private List<List<String>> parseStringToList(String input) {

        List<List<String>> result = new ArrayList<>();
        String regex = "\\[([^\\[\\]]+)\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        while (matcher.find()) {
            String match = matcher.group(1);
            String[] values = match.split(",\\s*");

            List<String> innerList = Arrays.stream(values)
                    .map(value -> value.replaceAll("\"", "").trim())
                    .collect(Collectors.toList());

            result.add(innerList);
        }

        return result;
    }
}

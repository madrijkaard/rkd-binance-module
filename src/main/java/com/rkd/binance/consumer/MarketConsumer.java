package com.rkd.binance.consumer;

import com.rkd.binance.client.BinanceSpotClient;
import com.rkd.binance.converter.KlineConverter;
import com.rkd.binance.facade.CandlestickFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.rkd.binance.definition.GroupDefinition.KLINE_CASSANDRA_GROUP;
import static com.rkd.binance.definition.TopicDefinition.KLINE_TOPIC;

/**
 * Class responsible for consuming market events from a Kafka cluster.
 */
@Component
public class MarketConsumer {

    @Autowired
    private KlineConverter klineConverter;
    @Autowired
    private CandlestickFacade candlestickFacade;

    /**
     * Method responsible for consuming messages sent to kline-topic.
     * <p>
     * Producer who will send messages {@link com.rkd.binance.producer.KlineProducer#sendMessage(List) here}.
     *
     * @param klineListAsString candlesticks in String format
     */
    @KafkaListener(topics = KLINE_TOPIC, groupId = KLINE_CASSANDRA_GROUP)
    public void receiveMessage(String klineListAsString) {
        var klineList = parseStringToList(klineListAsString);
        var klineModelList = klineConverter.convertOf(klineList);
        candlestickFacade.saveMarket(klineModelList);
    }

    /**
     * Method responsible for converting a list of candlesticks in String format to a list structure.
     * <p>
     * The {@link BinanceSpotClient#listKlines(String, String, int) listKlines} service has a documented return example.
     *
     * @param klineListAsString list of candlesticks in String format
     * @return list of candlesticks
     */
    private List<List<String>> parseStringToList(String klineListAsString) {

        List<List<String>> result = new ArrayList<>();
        String regex = "\\[([^\\[\\]]+)\\]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(klineListAsString);

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

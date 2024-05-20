package com.rkd.binance.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkd.binance.client.BinanceSpotClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.rkd.binance.definition.TopicDefinition.KLINE_TOPIC;

/**
 * Class responsible for publishing market events to a Kafka cluster.
 */
@Component
public class KlineProducer {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    /**
     * Method responsible for sending candlestick information to kline-topic topic.
     * <p>
     * The {@link BinanceSpotClient#listKlines(String, String, int) listKlines} service has a documented return example.
     *
     * @param klineList list of candlesticks
     */
    public void sendMessage(List<List<String>> klineList) {
        String serializedMessage = convertListToString(klineList);
        kafkaTemplate.send(KLINE_TOPIC, serializedMessage);
    }

    /**
     * Method responsible for converting a list of candlesticks into a String.
     *
     * @param klineList list of candlesticks
     * @return candlesticks in String format
     * @exception JsonProcessingException issues encountered when processing (parsing, generating) JSON content that are not pure I/O issues
     */
    private String convertListToString(List<List<String>> klineList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(klineList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

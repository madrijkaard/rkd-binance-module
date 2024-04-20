package com.rkd.binance.producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.rkd.binance.definition.TopicDefinition.KLINE_TOPIC;

@Component
public class KlineProducer {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public void sendMessage(List<List<String>> klineList) {
        String serializedMessage = convertListToString(klineList);
        kafkaTemplate.send(KLINE_TOPIC, serializedMessage);
    }

    private String convertListToString(List<List<String>> klineList) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(klineList);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

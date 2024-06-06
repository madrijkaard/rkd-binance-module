package com.rkd.binance.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkd.binance.response.ErrorTradeResponse;
import com.rkd.binance.response.TradeResponse;
import com.rkd.binance.response.TradeSpotResponse;

import java.util.Stack;

public class TradeJournalFactory {

    Stack<TradeSpotResponse> trades = new Stack<>();
    Stack<ErrorTradeResponse> errors = new Stack<>();

    private TradeJournalFactory() {}

    private static class TradeJournalHelper {
        private static final TradeJournalFactory INSTANCE = new TradeJournalFactory();
    }

    public static TradeJournalFactory getInstance() {
        return TradeJournalFactory.TradeJournalHelper.INSTANCE;
    }

    public void add(String response) {

        var tradeResponse = build(response);

        if (tradeResponse instanceof ErrorTradeResponse errorTradeResponse) {
            errors.add(errorTradeResponse);
        } else if (tradeResponse instanceof TradeSpotResponse tradeSpotResponse) {
            trades.add(tradeSpotResponse);
        }
    }

    private TradeResponse build(String response) {

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            return response.contains("\"code\"") ?
                    objectMapper.readValue(response, ErrorTradeResponse.class) :
                    objectMapper.readValue(response, TradeSpotResponse.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public Stack<TradeSpotResponse> getTrades() {
        return trades;
    }

    public Stack<ErrorTradeResponse> getErrors() {
        return errors;
    }
}

package com.rkd.binance.factory;

import com.rkd.binance.response.ErrorTradeResponse;
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

    public Stack<TradeSpotResponse> getTrades() {
        return trades;
    }

    public Stack<ErrorTradeResponse> getErrors() {
        return errors;
    }
}

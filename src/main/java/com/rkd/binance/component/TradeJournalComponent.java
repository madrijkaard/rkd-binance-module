package com.rkd.binance.component;

import com.rkd.binance.response.ErrorTradeResponse;
import com.rkd.binance.response.TradeSpotResponse;

import java.util.ArrayDeque;
import java.util.Deque;

public class TradeJournalComponent {

    private Deque<TradeSpotResponse> trades = new ArrayDeque<>();
    private Deque<ErrorTradeResponse> errors = new ArrayDeque<>();

    private TradeJournalComponent() {
    }

    private static class TradeJournalHelper {
        private static final TradeJournalComponent INSTANCE = new TradeJournalComponent();
    }

    public static TradeJournalComponent getInstance() {
        return TradeJournalComponent.TradeJournalHelper.INSTANCE;
    }

    public Deque<TradeSpotResponse> getTrades() {
        return trades;
    }

    public Deque<ErrorTradeResponse> getErrors() {
        return errors;
    }

    public void pushTrade(TradeSpotResponse trade) {
        trades.push(trade);
    }

    public TradeSpotResponse popTrade() {
        return trades.pop();
    }

    public TradeSpotResponse peekTrade() {
        return trades.peek();
    }

    public void pushError(ErrorTradeResponse error) {
        errors.push(error);
    }

    public ErrorTradeResponse popError() {
        return errors.pop();
    }

    public ErrorTradeResponse peekError() {
        return errors.peek();
    }
}

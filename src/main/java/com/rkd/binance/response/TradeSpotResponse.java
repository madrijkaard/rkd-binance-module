package com.rkd.binance.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rkd.binance.dto.FillDto;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TradeSpotResponse(
        String symbol,
        long orderId,
        String clientOrderId,
        long transactTime,
        String price,
        String origQty,
        String executedQty,
        String cummulativeQuoteQty,
        String status,
        String timeInForce,
        String type,
        String side,
        long workingTime,
        List<FillDto> fills,
        String selfTradePreventionMode) {
}

package com.rkd.binance.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ErrorTradeResponse(int code, String msg) implements TradeResponse {
}

package com.rkd.binance.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record FillDto(String price, String qty, String commission, String commissionAsset, long tradeId) {
}

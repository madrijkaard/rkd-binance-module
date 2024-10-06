package com.rkd.binance.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Class responsible for representing an exception from a service on Binance
 *
 * @param code error code
 * @param message error message
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public record ErrorTradeResponse(int code, String message){
}

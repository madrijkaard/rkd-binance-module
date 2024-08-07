package com.rkd.binance.exception;

import com.rkd.binance.response.ErrorTradeResponse;
import feign.FeignException;

public class CustomFeignException extends FeignException {

    private final ErrorTradeResponse errorTradeResponse;

    public CustomFeignException(int status, String message, ErrorTradeResponse errorTradeResponse) {
        super(status, message);
        this.errorTradeResponse = errorTradeResponse;
    }

    public ErrorTradeResponse getErrorTradeResponse() {
        return errorTradeResponse;
    }
}

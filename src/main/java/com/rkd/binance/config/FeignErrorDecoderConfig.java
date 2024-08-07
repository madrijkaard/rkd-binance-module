package com.rkd.binance.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkd.binance.exception.CustomFeignException;
import com.rkd.binance.factory.TradeJournalFactory;
import com.rkd.binance.response.ErrorTradeResponse;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

/**
 * Custom class to handle errors when making an HTTP call using Feign.
 */
public class FeignErrorDecoderConfig implements ErrorDecoder {

    /**
     * Method responsible for intercepting and handling errors that occur during HTTP calls.
     *
     * @param s HTTP method that was called (e.g. GET, POST)
     * @param response response object that contains details about the error (HTTP status, headers, response body, etc.)
     * @return instance of CustomFeignException
     */
    @Override
    public Exception decode(String s, Response response) {

        CustomFeignException customFeignException = null;

        try {
            int status = response.status();
            ErrorTradeResponse errorTradeResponse = new ObjectMapper().readValue(response.body().asInputStream(), ErrorTradeResponse.class);
            customFeignException = new CustomFeignException(status, response.reason(), errorTradeResponse);
        } catch (IOException e) {
            ErrorTradeResponse errorTradeResponse = new ErrorTradeResponse(-1, e.getMessage());
            TradeJournalFactory.getInstance().getErrors().add(errorTradeResponse);
        }
        return customFeignException;
    }
}

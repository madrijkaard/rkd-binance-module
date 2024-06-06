package com.rkd.binance.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rkd.binance.exception.BadRequestException;
import com.rkd.binance.exception.InternalServerErrorException;
import com.rkd.binance.response.ErrorTradeResponse;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;

public class FeignDecoderConfig implements ErrorDecoder {

    private final ErrorDecoder defaultDecoder = new Default();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Exception decode(String s, Response response) {

        ErrorTradeResponse errorTradeResponse = null;
        try {
            if (response.body() != null) {
                errorTradeResponse = objectMapper.readValue(response.body().asInputStream(), ErrorTradeResponse.class);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return switch (response.status()) {
            case 400 -> new BadRequestException("Bad Request");
            case 500 -> new InternalServerErrorException("Internal Server Error");
            default -> defaultDecoder.decode(s, response);
        };
    }

    private String build()
}

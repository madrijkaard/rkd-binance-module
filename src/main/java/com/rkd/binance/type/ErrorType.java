package com.rkd.binance.type;

import java.util.Arrays;

import static com.rkd.binance.definition.ExceptionDefinition.ILLEGAL_ARGUMENT;

public enum ErrorType {

    UNKNOWN(-1000, "An unknown error occurred while processing the request."),
    DISCONNECTED(-1001, "Internal error; unable to process your request. Please try again."),
    UNAUTHORIZED(-1002, "You are not authorized to execute this request."),
    TOO_MANY_REQUESTS(-1008, "Too many requests queued."),
    UNKNOWN_ORDER_COMPOSITION(-1014, "Unsupported order combination."),
    TOO_MANY_ORDERS(-1015, "Too many new orders."),
    SERVICE_SHUTTING_DOWN(-1016, "This service is no longer available."),
    UNSUPPORTED_OPERATION(-1020, "This operation is not supported."),
    INVALID_TIMESTAMP(-1021, "Timestamp for this request is outside of the recvWindow."),
    INVALID_SIGNATURE(-1022, "Signature for this request is not valid."),
    ILLEGAL_CHARS(-1100, "Illegal characters found in a parameter."),
    TOO_MANY_PARAMETERS(-1101, "Too many parameters sent for this endpoint."),
    MANDATORY_PARAM_EMPTY_OR_MALFORMED(-1102, "A mandatory parameter was not sent, was empty/null, or malformed."),
    UNKNOWN_PARAM(-1103, "An unknown parameter was sent."),
    UNREAD_PARAMETERS(-1104, "Not all sent parameters were read."),
    PARAM_EMPTY(-1105, "A parameter was empty."),
    PARAM_NOT_REQUIRED(-1106, "A parameter was sent when not required."),
    BAD_PRECISION(-1111, "Precision is over the maximum defined for this asset."),
    INVALID_TIF(-1115, "Invalid timeInForce."),
    INVALID_ORDER_TYPE(-1116, "Invalid orderType."),
    INVALID_SIDE(-1117, "Invalid side."),
    EMPTY_NEW_CL_ORD_ID(-1118, "New client order ID was empty."),
    EMPTY_ORG_CL_ORD_ID(-1119, "Original client order ID was empty."),
    BAD_INTERVAL(-1120, "Invalid interval."),
    BAD_SYMBOL(-1121, "Invalid symbol."),
    INVALID_LISTEN_KEY(-1125, "This listenKey does not exist."),
    MORE_THAN_XX_HOURS(-1127, "Lookup interval is too big."),
    BAD_CONTRACT(-1128, "Invalid underlying."),
    BAD_CURRENCY(-1129, "Invalid asset."),
    INVALID_PARAMETER(-1130, "Invalid data sent for a parameter."),
    BAD_RECV_WINDOW(-1131, "recvWindow must be less than 60000."),
    NEW_ORDER_REJECTED(-2010, "NEW_ORDER_REJECTED."),
    NO_SUCH_ORDER(-2013, "Order does not exist."),
    BAD_API_KEY_FMT(-2014, "API-key format invalid."),
    INVALID_API_KEY(-2015, "Invalid API-key, IP, or permissions for action."),
    BALANCE_NOT_SUFFICIENT(-2018, "Balance is insufficient."),
    OPTION_MARGIN_NOT_SUFFICIENT(-2027, "Option margin is insufficient."),
    TRANSFER_FAILED(-3029, "Asset transfer fail."),
    PRICE_LESS_THAN_ZERO(-4001, "Price less than 0."),
    PRICE_GREATER_THAN_MAX_PRICE(-4002, "Price greater than max price."),
    QTY_LESS_THAN_ZERO(-4003, "Quantity less than zero."),
    QTY_LESS_THAN_MIN_QTY(-4004, "Quantity less than min quantity."),
    QTY_GREATER_THAN_MAX_QTY(-4005, "Quantity greater than max quantity."),
    PRICE_LESS_THAN_MIN_PRICE(-4013, "Price less than min price."),
    INVALID_TICK_SIZE_PRECISION(-4029, "Tick size precision is invalid."),
    INVALID_QTY_PRECISION(-4030, "Step size precision is invalid."),
    AMOUNT_MUST_BE_POSITIVE(-4055, "Amount must be positive.");

    private final int code;
    private final String message;

    ErrorType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorType of(int code) {
        return Arrays.stream(ErrorType.values())
                .filter(errorType -> errorType.code == code).findFirst()
                .orElseThrow(() -> new IllegalArgumentException(ILLEGAL_ARGUMENT.concat("error code Binance api")));
    }
}

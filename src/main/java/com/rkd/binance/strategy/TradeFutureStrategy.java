package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceFutureClient;
import com.rkd.binance.factory.CredentialFactory;
import com.rkd.binance.type.*;
import com.rkd.binance.type.PositionSideType;
import com.rkd.binance.util.RequestUtil;
import com.rkd.binance.util.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Service that provides strategies for trading futures on Binance.
 */
@Service
public class TradeFutureStrategy {

    @Autowired
    private BinanceFutureClient binanceFutureClient;

    /**
     *
     *
     * @param symbol The trading pair symbol, e.g., "BTCUSDT"
     * @param margin The margin type, either "ISOLATED" or "CROSSED"
     * @param leverage The leverage level to be set
     * @param side The order side, either "BUY" or "SELL"
     * @param positionSide The position side, e.g., "LONG" or "SHORT"
     * @param order The order type, e.g., "LIMIT" or "MARKET"
     * @param quantity The quantity of the order
     */
    public void tradeFuture(String symbol, String margin, int leverage, String side, String positionSide, String order, String quantity) {
        prepare(symbol, margin, leverage, side, positionSide, order, quantity);
    }

    /**
     *
     *
     * @param symbol The trading pair symbol, e.g., "BTCUSDT"
     * @param margin The margin type, either "ISOLATED" or "CROSSED"
     * @param leverage The leverage level to be set
     * @param side The order side, either "BUY" or "SELL"
     * @param positionSide The position side, e.g., "LONG" or "SHORT"
     * @param order The order type, e.g., "LIMIT" or "MARKET"
     * @param quantity The quantity of the order
     */
    private void prepare(String symbol, String margin, int leverage, String side, String positionSide, String order, String quantity) {

        var symbolType = SymbolType.of(symbol);
        var marginType = MarginType.of(margin);
        var leverageType = LeverageType.of(leverage);
        var sideType = DecisionType.of(side);
        var positionSideType = PositionSideType.of(positionSide);
        var orderType = OrderType.of(order);

        changeMarginType(symbolType, marginType);
        setLeverage(symbolType, leverageType);
        placeOrder(symbolType, sideType, positionSideType, orderType, quantity);
    }

    /**
     * Changes the margin type for a specified trading pair.
     *
     * @param symbolType     The trading pair symbol, e.g., "BTCUSDT"
     * @param marginType The margin type, either "ISOLATED" or "CROSSED"
     */
    private void changeMarginType(SymbolType symbolType, MarginType marginType) {

        long milliseconds = System.currentTimeMillis();

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("symbol", symbolType.getSymbol());
        parameters.put("marginType", marginType.name());
        parameters.put("timestamp", String.valueOf(milliseconds));

        var queryPath = RequestUtil.joinQueryParameters(parameters);
        var signature = SignatureUtil.getSignature(queryPath, CredentialFactory.getInstance().getSecret());

        binanceFutureClient.changeMarginType(APPLICATION_JSON_VALUE, CredentialFactory.getInstance().getKey(), symbolType.getSymbol(), MarginType.ISOLATED.name(), String.valueOf(milliseconds), signature);
    }

    /**
     * Sets the leverage for a specified trading pair.
     *
     * @param symbolType   The trading pair symbol, e.g., "BTCUSDT"
     * @param leverageType The leverage level to be set
     */
    private void setLeverage(SymbolType symbolType, LeverageType leverageType) {

        long milliseconds = System.currentTimeMillis();

        HashMap<String, String> params = new HashMap<>();
        params.put("symbol", symbolType.getSymbol());
        params.put("leverage", String.valueOf(leverageType.getLeverage()));
        params.put("timestamp", String.valueOf(milliseconds));

        var queryPath = RequestUtil.joinQueryParameters(params);
        var signature = SignatureUtil.getSignature(queryPath, CredentialFactory.getInstance().getSecret());

        binanceFutureClient.setLeverage(APPLICATION_JSON_VALUE, CredentialFactory.getInstance().getKey(), symbolType.getSymbol(), String.valueOf(leverageType), String.valueOf(milliseconds), signature);
    }

    /**
     * Places an order in the futures market.
     *
     * @param symbolType       The trading pair symbol, e.g., "BTCUSDT"
     * @param decisionType         The order side, either "BUY" or "SELL"
     * @param positionSideType The position side, e.g., "LONG" or "SHORT"
     * @param orderType         The order type, e.g., "LIMIT" or "MARKET"
     * @param quantity     The quantity of the order
     */
    private void placeOrder(SymbolType symbolType, DecisionType decisionType, PositionSideType positionSideType, OrderType orderType, String quantity) {

        long milliseconds = System.currentTimeMillis();

        HashMap<String, String> params = new HashMap<>();
        params.put("symbol", symbolType.getSymbol());
        params.put("side", decisionType.name());
        params.put("positionSide", positionSideType.name());
        params.put("type", orderType.name());
        params.put("quantity", quantity);
        params.put("timestamp", String.valueOf(milliseconds));

        var queryPath = RequestUtil.joinQueryParameters(params);
        var signature = SignatureUtil.getSignature(queryPath, CredentialFactory.getInstance().getSecret());

        binanceFutureClient.placeOrder(APPLICATION_JSON_VALUE, CredentialFactory.getInstance().getKey(), symbolType.getSymbol(),
                decisionType.name(), positionSideType.name(), positionSideType.name(), quantity, String.valueOf(milliseconds), signature);
    }
}

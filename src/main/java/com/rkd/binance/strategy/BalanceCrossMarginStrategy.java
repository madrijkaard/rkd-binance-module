package com.rkd.binance.strategy;

import com.rkd.binance.client.BinanceFutureClient;
import com.rkd.binance.converter.BalanceCrossMarginConverter;
import com.rkd.binance.dto.CrossMarginWalletDto;
import com.rkd.binance.component.CredentialComponent;
import com.rkd.binance.util.RequestUtil;
import com.rkd.binance.util.SignatureUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * Balance strategy for futures markets account.
 */
@Service
public class BalanceCrossMarginStrategy {

    @Autowired
    private BinanceFutureClient binanceFutureClient;
    @Autowired
    private BalanceCrossMarginConverter balanceCrossMarginConverter;
    @Autowired
    private CredentialComponent credentialComponent;

    /**
     * Method responsible for obtaining the balance of an account and futures markets.
     *
     * @return list containing balance information for various assets
     */
    public List<CrossMarginWalletDto> getBalance() {

        long milliseconds = System.currentTimeMillis();

        HashMap<String, String> parameters = new HashMap<>();
        parameters.put("timestamp", String.valueOf(milliseconds));

        var queryPath = RequestUtil.joinQueryParameters(parameters);
        var signature = SignatureUtil.getSignature(queryPath, credentialComponent.getSecret());

        var balanceCrossMarginDtoList = binanceFutureClient.getBalanceFuture(
                APPLICATION_JSON_VALUE, credentialComponent.getKey(),
                String.valueOf(milliseconds), signature);

        return balanceCrossMarginConverter.convertOf(balanceCrossMarginDtoList);
    }
}

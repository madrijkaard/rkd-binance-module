package com.rkd.binance.strategy;

import com.rkd.binance.dto.SpotWalletDto;
import com.rkd.binance.type.CryptoType;
import com.rkd.binance.type.StrategyType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.rkd.binance.type.CryptoType.exist;
import static com.rkd.binance.type.DecisionType.*;
import static com.rkd.binance.type.VectorType.UP;

/**
 *
 */
@Service
public class ExecuteSpotStrategy {

    @Autowired
    private PrepareTradeStrategy executeTradeStrategy;
    @Autowired
    private TradeSpotStrategy tradeSpotStrategy;

    /**
     * Method responsible for starting the negotiation process. The quantity of cryptocurrencies and stable currency available
     * in the wallet is checked. There is a minimum amount for the process to continue. Then it is checked which strategy is configured.
     *
     * @param spotWalletDto
     * @param stableCoin
     * @param spotMinimumRange
     * @param spotMaximumRange
     * @param strategyTypeList
     * @param spotRank
     * @param haveMoney
     */
    public void initialize(SpotWalletDto spotWalletDto, List<CryptoType> spotRank, String stableCoin, float spotMinimumRange, float spotMaximumRange,
                               List<StrategyType> strategyTypeList, boolean haveMoney) {

        spotWalletDto.cryptos().forEach(

                (cryptoType, money) -> {

                    if (!exist(spotRank, cryptoType))
                        return;

                    var symbol = cryptoType.name() + stableCoin;

                    var decision = executeTradeStrategy.prepare(symbol, UP.name(), spotMinimumRange, spotMaximumRange, strategyTypeList);

                    if (isCrossBuy(decision) && haveMoney) {

                    }

                    if (isCrossSell(decision)) {

                    }
                });
    }
}

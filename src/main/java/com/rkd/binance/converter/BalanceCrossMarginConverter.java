package com.rkd.binance.converter;

import com.rkd.binance.dto.BalanceCrossMarginDto;
import com.rkd.binance.dto.CrossMarginWalletDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BalanceCrossMarginConverter {

    public List<CrossMarginWalletDto> convertOf(List<BalanceCrossMarginDto> balanceCrossMarginDtoList) {
        return balanceCrossMarginDtoList.stream().map(this::buildCrossMarginWalletDto).collect(Collectors.toList());
    }

    private CrossMarginWalletDto buildCrossMarginWalletDto(BalanceCrossMarginDto balanceCrossMarginDto) {
        return new CrossMarginWalletDto(balanceCrossMarginDto.asset(),
                        balanceCrossMarginDto.balance(),
                        balanceCrossMarginDto.crossWalletBalance(),
                        balanceCrossMarginDto.availableBalance(),
                        balanceCrossMarginDto.crossUnPnl());
    }
}

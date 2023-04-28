package com.example.neighbour.dto.transactions;

import com.example.neighbour.dto.business.BusinessDtoTest;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.dto.users.UserDtoTest;
import com.example.neighbour.enums.Currency;
import com.example.neighbour.enums.TransactionMethod;
import com.example.neighbour.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseTransactionsDtoTest {

    public static ResponseTransactionsDto getResponseTransactionsDto(){
        return ResponseTransactionsDto.builder()
                .transactionFee(BigDecimal.ZERO)
                .transactionMethod(TransactionMethod.STRIPE)
                .transactionStatus(TransactionStatus.SUCCESSFUL)
                .transactionCurrency(Currency.GBP)
                .transactionDate(LocalDate.now())
                .amount(BigDecimal.TEN)
                .fromUser(UserDtoTest.getUserDto())
                .toBusiness(BusinessDtoTest.getBusinessDto())
                .build();
    }
}
package com.example.neighbour.dto.transactions;

import com.example.neighbour.dto.users.UserDtoTest;
import com.example.neighbour.enums.Currency;
import com.example.neighbour.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class BusinessTransactionsDtoTest {
    public static BusinessTransactionsDto createBusinessTransactionsDto() {
        return BusinessTransactionsDto.builder()
                .amount(BigDecimal.TEN)
                .fromUser(UserDtoTest.getUserDto())
                .transactionStatus(TransactionStatus.SUCCESSFUL)
                .transactionDate(LocalDate.now())
                .transactionCurrency(Currency.GBP)
                .transactionFee(BigDecimal.ZERO)
                .build();
    }
}
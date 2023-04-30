package com.example.neighbour.dto.transactions;

import com.example.neighbour.data.BusinessTest;
import com.example.neighbour.data.Transactions;
import com.example.neighbour.data.UserTest;
import com.example.neighbour.enums.Currency;
import com.example.neighbour.enums.TransactionMethod;
import com.example.neighbour.enums.TransactionStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class TransactionsDtoTest {

    public static TransactionsDto getTransactionsDto() {
        return TransactionsDto.builder()
                .amount(BigDecimal.valueOf(20))
                .fromUser(UserTest.getNormalUser())
                .toBusiness(BusinessTest.createBusiness())
                .transactionMethod(TransactionMethod.CASH)
                .transactionStatus(TransactionStatus.PENDING)
                .transactionDate(LocalDate.now())
                .transactionCurrency(Currency.EUR)
                .transactionFee(BigDecimal.valueOf(0))
                .build();
    }

    public static List<Transactions> getTransactionsDtoList() {
        return List.of(
                getTransactionsDto().toEntity()
        );
    }

}
package com.example.neighbour.dto.transactions;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.Transactions;
import com.example.neighbour.data.User;
import com.example.neighbour.dto.business.BusinessDto;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.enums.Currency;
import com.example.neighbour.enums.TransactionMethod;
import com.example.neighbour.enums.TransactionStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link Transactions} entity
 */
@Builder
public record ResponseTransactionsDto(
        BigDecimal amount,
        UserDto fromUser,
        BusinessDto toBusiness,
        TransactionMethod transactionMethod,
        TransactionStatus transactionStatus,
        LocalDate transactionDate,
        Currency transactionCurrency,
        BigDecimal transactionFee
) {

    public ResponseTransactionsDto(Transactions transactions) {
        this(
                transactions.getAmount(),
                new UserDto(transactions.getFromUser()),
                new BusinessDto(transactions.getToBusiness()),
                transactions.getTransactionMethod(),
                transactions.getTransactionStatus(),
                transactions.getTransactionDate(),
                transactions.getTransactionCurrency(),
                transactions.getTransactionFee()
        );
    }
}

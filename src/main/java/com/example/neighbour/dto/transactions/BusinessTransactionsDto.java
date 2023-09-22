package com.example.neighbour.dto.transactions;

import com.example.neighbour.data.Transactions;
import com.example.neighbour.dto.users.UserDto;
import com.example.neighbour.dto.business.BusinessDto;
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
public record BusinessTransactionsDto(
        BigDecimal amount,
        UserDto fromUser,
        TransactionStatus transactionStatus,
        LocalDate transactionDate,
        Currency transactionCurrency,
        BigDecimal transactionFee
) {

    public BusinessTransactionsDto(Transactions transactions) {
        this(
                transactions.getAmount(),
                new UserDto(transactions.getFromUser()),
                transactions.getTransactionStatus(),
                transactions.getTransactionDate(),
                transactions.getTransactionCurrency(),
                transactions.getTransactionFee()
        );
    }
}

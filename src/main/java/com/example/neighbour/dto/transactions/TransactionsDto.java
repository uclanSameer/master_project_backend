package com.example.neighbour.dto.transactions;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.Transactions;
import com.example.neighbour.data.User;
import com.example.neighbour.enums.Currency;
import com.example.neighbour.enums.TransactionMethod;
import com.example.neighbour.enums.TransactionStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * A DTO for the {@link com.example.neighbour.data.Transactions} entity
 */
@Builder
public record TransactionsDto(
        BigDecimal amount,
        User fromUser,
        Business toBusiness,
        TransactionMethod transactionMethod,
        TransactionStatus transactionStatus,
        LocalDate transactionDate,
        Currency transactionCurrency,
        BigDecimal transactionFee
) {

    public TransactionsDto(Transactions transactions) {
        this(
                transactions.getAmount(),
                transactions.getFromUser(),
                transactions.getToBusiness(),
                transactions.getTransactionMethod(),
                transactions.getTransactionStatus(),
                transactions.getTransactionDate(),
                transactions.getTransactionCurrency(),
                transactions.getTransactionFee()
        );
    }

    public Transactions toEntity() {
        return Transactions.builder()
                .amount(amount)
                .fromUser(fromUser)
                .toBusiness(toBusiness)
                .transactionMethod(transactionMethod)
                .transactionStatus(transactionStatus)
                .transactionDate(transactionDate)
                .transactionCurrency(transactionCurrency)
                .transactionFee(transactionFee)
                .build();
    }


}

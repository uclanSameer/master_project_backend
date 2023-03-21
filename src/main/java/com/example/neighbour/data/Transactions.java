package com.example.neighbour.data;


import com.example.neighbour.enums.Currency;
import com.example.neighbour.enums.TransactionMethod;
import com.example.neighbour.enums.TransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transactions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "amount")
    private BigDecimal amount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User fromUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_id")
    private Business toBusiness;

    @Column(name = "transaction_method")
    @Enumerated(EnumType.STRING)
    private TransactionMethod transactionMethod;

    @Column(name = "transaction_status")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(name = "transaction_date")
    private LocalDate transactionDate;

    @Column(name = "transaction_reference", unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String transactionReference;

    @Column(name = "transaction_currency")
    @Enumerated(EnumType.STRING)
    private Currency transactionCurrency;

    @Column(name = "transaction_fee")
    private BigDecimal transactionFee;


}

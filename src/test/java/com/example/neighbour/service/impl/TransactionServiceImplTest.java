package com.example.neighbour.service.impl;

import com.example.neighbour.data.BusinessTest;
import com.example.neighbour.data.UserTest;
import com.example.neighbour.dto.transactions.BusinessTransactionsDto;
import com.example.neighbour.dto.transactions.ResponseTransactionsDto;
import com.example.neighbour.dto.transactions.TransactionsDto;
import com.example.neighbour.dto.transactions.TransactionsDtoTest;
import com.example.neighbour.repositories.TransactionRepository;
import com.example.neighbour.service.BusinessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    TransactionRepository repository;

    @Mock
    BusinessService businessService;

    @InjectMocks
    TransactionServiceImpl transactionService;

    @Test
    void createTransaction() {
        when(repository.save(any()))
                .thenReturn(TransactionsDtoTest.getTransactionsDto().toEntity());

        transactionService.createTransaction(TransactionsDtoTest.getTransactionsDto());
    }

    @Test
    void getAllTransactions() {
        when(repository.findAll())
                .thenReturn(TransactionsDtoTest.getTransactionsDtoList());

        List<ResponseTransactionsDto> allTransactions = transactionService.getAllTransactions();

        assertEquals(1, allTransactions.size());
    }

    @Test
    void getAllTransactionsToBusiness() {
        when(businessService.getCurrentBusiness())
                .thenReturn(BusinessTest.createBusiness());
        when(repository.findByToBusiness(any()))
                .thenReturn(
                        Optional.of(
                                TransactionsDtoTest.getTransactionsDtoList()
                        )
                );
        List<BusinessTransactionsDto> allTransactions = transactionService.getAllTransactionsToBusiness();
        assertEquals(1, allTransactions.size());
    }

    @Test
    void getAllTransactionsToBusinessId(){
        when(repository.findByToBusiness(any()))
                .thenReturn(
                        Optional.of(
                                TransactionsDtoTest.getTransactionsDtoList()
                        )
                );

        List<BusinessTransactionsDto> allTransactions = transactionService.getAllTransactionsToBusiness(BusinessTest.createBusiness());

        assertEquals(1, allTransactions.size());
    }

    @Test
    void getAllTransactionFromUser(){
        when(repository.findByFromUser(any()))
                .thenReturn(
                        Optional.of(
                                TransactionsDtoTest.getTransactionsDtoList()
                        )
                );

        List<TransactionsDto> allTransactions = transactionService.getAllTransactionsFromUser(
                UserTest.getNormalUser()
        );

        assertEquals(1, allTransactions.size());
    }

    @Test
    void getTransactionFromUserId(){
        when(repository.findByFromUserId(anyInt()))
                .thenReturn(
                        Optional.of(
                                TransactionsDtoTest.getTransactionsDtoList()
                        )
                );

        List<TransactionsDto> allTransactions = transactionService.getAllTransactionsFromUserId(1
        );

        assertEquals(1, allTransactions.size());
    }
}
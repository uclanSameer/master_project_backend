package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.transactions.BusinessTransactionsDto;
import com.example.neighbour.dto.transactions.BusinessTransactionsDtoTest;
import com.example.neighbour.dto.transactions.ResponseTransactionsDto;
import com.example.neighbour.dto.transactions.ResponseTransactionsDtoTest;
import com.example.neighbour.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionControllerTest {

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private TransactionController transactionController;

    @Test
    void getTransactionForBusiness() {
        when(transactionService.getAllTransactionsToBusiness()).thenReturn(
                List.of(BusinessTransactionsDtoTest.createBusinessTransactionsDto())
        );

        ResponseDto<List<BusinessTransactionsDto>> transactionForBusiness = transactionController.getTransactionForBusiness();

        assertEquals(1, transactionForBusiness.getData().size());
        assertEquals("Transactions fetched successfully", transactionForBusiness.getMessage());
    }


    @Test
    void getAllTransactions() {
        when(transactionService.getAllTransactions()).thenReturn(
                List.of(ResponseTransactionsDtoTest.getResponseTransactionsDto())
        );

        ResponseDto<List<ResponseTransactionsDto>> allTransactions = transactionController.getAllTransactions();

        assertEquals(1, allTransactions.getData().size());
        assertEquals("Transactions fetched successfully", allTransactions.getMessage());
    }


}
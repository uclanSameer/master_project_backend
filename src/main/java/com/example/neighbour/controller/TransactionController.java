package com.example.neighbour.controller;

import com.example.neighbour.dto.ResponseDto;
import com.example.neighbour.dto.transactions.BusinessTransactionsDto;
import com.example.neighbour.dto.transactions.ResponseTransactionsDto;
import com.example.neighbour.service.TransactionService;
import com.example.neighbour.utils.ApiConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping(ApiConstants.API_VERSION_1 + "transactions")
public class TransactionController {
    private final TransactionService transactionService;

    @GetMapping("/business")
    public ResponseDto<List<BusinessTransactionsDto>> getTransactionForBusiness() {
        log.info("Getting transactions for business");
        List<BusinessTransactionsDto> allTransactionsToBusiness = transactionService.getAllTransactionsToBusiness();

        return ResponseDto.success(allTransactionsToBusiness, "Transactions fetched successfully");
    }

    @GetMapping("/all")
    public ResponseDto<List<ResponseTransactionsDto>> getAllTransactions() {
        log.info("Getting all transactions");
        List<ResponseTransactionsDto> allTransactions = transactionService.getAllTransactions();

        return ResponseDto.success(allTransactions, "Transactions fetched successfully");

    }
}

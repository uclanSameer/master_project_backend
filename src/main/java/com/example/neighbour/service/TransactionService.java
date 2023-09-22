package com.example.neighbour.service;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.User;
import com.example.neighbour.dto.transactions.BusinessTransactionsDto;
import com.example.neighbour.dto.transactions.ResponseTransactionsDto;
import com.example.neighbour.dto.transactions.TransactionsDto;

import java.util.List;

public interface TransactionService {

    void createTransaction(TransactionsDto transaction);

    List<ResponseTransactionsDto> getAllTransactions();

    List<BusinessTransactionsDto> getAllTransactionsToBusiness();
    List<BusinessTransactionsDto> getAllTransactionsToBusiness(Business business);

    List<TransactionsDto> getAllTransactionsToBusinessId(Integer businessId);


    List<TransactionsDto> getAllTransactionsFromUser(User user);

    List<TransactionsDto> getAllTransactionsFromUserId(Integer userId);


}

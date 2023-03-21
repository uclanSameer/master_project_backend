package com.example.neighbour.service;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.User;
import com.example.neighbour.dto.TransactionsDto;

import java.util.List;

public interface TransactionService {

    void createTransaction(TransactionsDto transaction);

    List<TransactionsDto> getAllTransactions();

    List<TransactionsDto> getAllTransactionsToBusiness(Business business);

    List<TransactionsDto> getAllTransactionsToBusinessId(Integer businessId);


    List<TransactionsDto> getAllTransactionsFromUser(User user);

    List<TransactionsDto> getAllTransactionsFromUserId(Integer userId);


}

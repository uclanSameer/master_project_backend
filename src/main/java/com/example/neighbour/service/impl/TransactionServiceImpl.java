package com.example.neighbour.service.impl;

import com.example.neighbour.data.Business;
import com.example.neighbour.data.Transactions;
import com.example.neighbour.data.User;
import com.example.neighbour.dto.TransactionsDto;
import com.example.neighbour.exception.ErrorResponseException;
import com.example.neighbour.repositories.TransactionRepository;
import com.example.neighbour.service.TransactionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository repository;

    @Override
    public void createTransaction(TransactionsDto transaction) {
        try {
            repository.save(transaction.toEntity());
        } catch (Exception e) {
            log.error("Error creating transaction: {}", e.getMessage());
            throw new ErrorResponseException(500, "Error creating transaction");
        }
    }

    @Override
    public List<TransactionsDto> getAllTransactions() {
        try {
            return repository.findAll()
                    .stream()
                    .map(TransactionsDto::new)
                    .toList();
        } catch (Exception e) {
            log.error("Error getting all transactions: {}", e.getMessage());
            throw new ErrorResponseException(500, "Error getting all transactions");
        }
    }

    @Override
    public List<TransactionsDto> getAllTransactionsToBusiness(Business business) {
        try {
            Optional<List<Transactions>> transactions = repository.findByToBusiness(business);
            return transactions
                    .map(transaction -> transaction
                            .stream()
                            .map(TransactionsDto::new)
                            .toList())
                    .orElseThrow(() -> new ErrorResponseException(404, "No transactions found for business"));
        } catch (Exception e) {
            log.error("Error getting all transactions to business: {}", e.getMessage());
            throw new ErrorResponseException(500, "Error getting all transactions to business");
        }
    }

    @Override
    public List<TransactionsDto> getAllTransactionsToBusinessId(Integer businessId) {
        try {
            Optional<List<Transactions>> transactions = repository.findByToBusinessId(businessId);
            return transactions
                    .map(transaction -> transaction
                            .stream()
                            .map(TransactionsDto::new)
                            .toList())
                    .orElseThrow(() -> new ErrorResponseException(404, "No transactions found for business"));
        } catch (Exception e) {
            log.error("Error getting all transactions to business: {}", e.getMessage());
            throw new ErrorResponseException(500, "Error getting all transactions to business");
        }
    }

    @Override
    public List<TransactionsDto> getAllTransactionsFromUser(User user) {
        try {
            Optional<List<Transactions>> transactions = repository.findByFromUser(user);
            return transactions
                    .map(transaction -> transaction
                            .stream()
                            .map(TransactionsDto::new)
                            .toList())
                    .orElseThrow(() -> new ErrorResponseException(404, "No transactions found for user"));
        } catch (Exception e) {
            log.error("Error getting all transactions from user: {}", e.getMessage());
            throw new ErrorResponseException(500, "Error getting all transactions from user");
        }
    }

    @Override
    public List<TransactionsDto> getAllTransactionsFromUserId(Integer userId) {
        try {
            Optional<List<Transactions>> transactions = repository.findByFromUserId(userId);
            return transactions
                    .map(transaction -> transaction
                            .stream()
                            .map(TransactionsDto::new)
                            .toList())
                    .orElseThrow(() -> new ErrorResponseException(404, "No transactions found for user"));
        } catch (Exception e) {
            log.error("Error getting all transactions from user: {} with message : {}", userId, e.getMessage());
            throw new ErrorResponseException(500, "Error getting all transactions from user");
        }
    }
}

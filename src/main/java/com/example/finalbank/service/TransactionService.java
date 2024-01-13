package com.example.finalbank.service;

import com.example.finalbank.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    List<TransactionDto> getAllTransactions();
    TransactionDto getTransactionById(int transactionId);
    List<TransactionDto>getTransactionsByAccount(int accountId);
    boolean removeTransaction(int transactionId);
}


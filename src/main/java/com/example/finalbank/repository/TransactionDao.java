package com.example.finalbank.repository;

import com.example.finalbank.entity.Transaction;

import java.util.List;

public interface TransactionDao {
        List<Transaction> getAllTransactions();
        Transaction getTransactionById(int transactionId);
        List<Transaction> getTransactionsByAccount(int AccountId);

        boolean removeTransaction(int transactionId);

}

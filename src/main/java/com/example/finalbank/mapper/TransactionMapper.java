package com.example.finalbank.mapper;

import com.example.finalbank.dto.TransactionDto;
import com.example.finalbank.entity.Transaction;



public class TransactionMapper {
    public Transaction toEntity(TransactionDto dto) {
        Transaction transaction = new Transaction();
        transaction.setTransactionId(dto.getTransactionId());
        transaction.setAmount(dto.getAmount());
        transaction.setTimestamp(dto.getTimestamp());
        transaction.setCurrency(dto.getCurrency());
        transaction.setDeleted(dto.getDeleted());
        return transaction;
    }

    public TransactionDto toDto(Transaction transaction) {
        return TransactionDto.builder()
                .transactionId(transaction.getTransactionId())
                .amount(transaction.getAmount())
                .timestamp(transaction.getTimestamp())
                .currency(transaction.getCurrency())
                .isDeleted(transaction.getDeleted())
                .build();
    }



}

package com.example.finalbank.service;

import com.example.finalbank.dto.TransactionDto;
import com.example.finalbank.mapper.TransactionMapper;
import com.example.finalbank.repository.TransactionDao;

import java.util.List;
import java.util.stream.Collectors;

public class TransactionServiceImpl implements TransactionService{

   TransactionMapper transactionMapper = new TransactionMapper();

    private final TransactionDao transactionDao;

    public TransactionServiceImpl(TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }

    @Override
    public List<TransactionDto> getAllTransactions() {
        return transactionDao.getAllTransactions()
                .stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public TransactionDto getTransactionById(int transactionId) {
        return transactionMapper.toDto(transactionDao.getTransactionById(transactionId));
    }

    @Override
    public List<TransactionDto> getTransactionsByAccount(int accountId) {

        return transactionDao.getTransactionsByAccount(accountId)
                .stream()
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public boolean removeTransaction(int transactionId) {
        return transactionDao.removeTransaction(transactionId);
       }
    }

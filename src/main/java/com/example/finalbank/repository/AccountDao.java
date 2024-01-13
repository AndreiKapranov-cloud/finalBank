package com.example.finalbank.repository;

import com.example.finalbank.entity.Account;

import java.sql.SQLException;
import java.util.List;

public interface AccountDao {
    List<Account> getAllAccounts();
    List<Account> getAccountsByBank(int bankId);
    Account getAccountById(int accountId);
    Account saveAccount(Account account) throws SQLException;
    boolean removeAccount(int accountId);
    List<Account> getAccountsByTransaction(int transactionId);
    void transfer(int amount, int fromAccountId,int toAccountId) throws SQLException;

    int depositWithoutTransaction(int amount,int accountId);

    void deposit(int amount,int accountId);
    int getBalance(int accountId);
    int withdraw(int amount, int accountId) throws SQLException;











}

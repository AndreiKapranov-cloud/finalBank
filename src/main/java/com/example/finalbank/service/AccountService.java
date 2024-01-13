package com.example.finalbank.service;

import com.example.finalbank.dto.AccountDto;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AccountService {
    List<AccountDto>getAccountsByBank(int bankId);

    AccountDto saveAccount(AccountDto accountDto) throws SQLException;
    boolean removeAccount(int accountId);
    List<AccountDto> getAllAccounts();
    AccountDto getAccountById(int accountId);

    List<AccountDto> getAccountsByTransaction(int transactionId);

   void transfer(int amount,  int fromAccountId, int toAccountId) throws SQLException;

    void deposit(int amount,Connection conn,int AccountId);
    int getBalance(Connection conn,int accountId);
    int withdraw(int amount, Connection conn, int accountId) throws SQLException;


}


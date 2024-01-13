package com.example.finalbank.service;

import com.example.finalbank.dto.AccountDto;
import com.example.finalbank.entity.Account;
import com.example.finalbank.mapper.AccountMapper;
import com.example.finalbank.repository.AccountDao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class AccountServiceImpl implements AccountService{
    AccountMapper accountMapper = new AccountMapper();

    private final AccountDao accountDao;

    public AccountServiceImpl(AccountDao accountDao) {
        this.accountDao = accountDao;
    }


    @Override
    public List<AccountDto> getAllAccounts() {
        return accountDao.getAllAccounts()
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public AccountDto getAccountById(int accountId) {

        return accountMapper.toDto(accountDao.getAccountById(accountId));

    }
    @Override
    public List<AccountDto> getAccountsByBank(int bankId) {
        return accountDao.getAccountsByBank(bankId)
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AccountDto saveAccount(AccountDto accountDto) throws SQLException {

       Account account = accountDao.saveAccount(accountMapper.toEntity(accountDto));
        return accountMapper.toDto(account);
    }

    @Override
    public List<AccountDto> getAccountsByTransaction(int transactionId) {
        return accountDao.getAccountsByTransaction(transactionId)
                .stream()
                .map(accountMapper::toDto)
                .collect(Collectors.toList());

    }

    @Override
    public boolean removeAccount(int accountId) {
        return accountDao.removeAccount(accountId);
    }

    @Override
    public void transfer(int amount, int fromAccountId, int toAccountId) throws SQLException {
        accountDao.transfer(amount,fromAccountId,toAccountId);

    }

    @Override
    public void deposit(int amount, Connection conn, int accountId) {
        accountDao.deposit(amount,accountId);
    }

    @Override
    public int getBalance(Connection conn, int accountId) {
        return accountDao.getBalance(accountId);
    }

    @Override
    public int withdraw(int amount, Connection conn, int accountId) throws SQLException {
        return accountDao.withdraw(amount,accountId);
    }
}

package com.example.finalbank.repository;

import com.example.finalbank.entity.Bank;

import java.sql.SQLException;
import java.util.List;

public interface BankDao {
    Bank saveBank(Bank bank);
    List<Bank> getAllBanks();
    Bank getBankByName(String name);
    Bank getBankByAccount(int AccountId);
    boolean updateBank(Bank bank) throws SQLException;
    boolean removeBank(int accountId);
    Bank getBankById(int bankId);
}

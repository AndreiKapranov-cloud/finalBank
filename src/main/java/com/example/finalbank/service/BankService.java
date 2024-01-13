package com.example.finalbank.service;

import com.example.finalbank.dto.BankDto;

import java.util.List;

public interface BankService {
    List<BankDto> getAllBanks();
    BankDto saveBank(BankDto bankDto);
    BankDto getBankByAccount(int accountId);
    BankDto getBankById(int bankId);
    boolean updateBank(BankDto bankDTO);

    boolean removeBank(int bankId);
}

package com.example.finalbank.mapper;

import com.example.finalbank.dto.AccountDto;
import com.example.finalbank.entity.Account;
public class AccountMapper {

    public Account toEntity(AccountDto dto) {
        Account account = new Account();
        account.setBalance(dto.getBalance());
        account.setAccountId(dto.getAccountId());
        account.setBankId(dto.getBankId());
        account.setDeleted(dto.getDeleted());
        return account;
    }

    public AccountDto toDto(Account account) {
        return AccountDto.builder()
                .accountId(account.getAccountId())
                .balance(account.getBalance())
                .bankId(account.getBankId())
                .isDeleted(account.getDeleted())
                .build();
    }
}
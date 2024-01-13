package com.example.finalbank.mapper;

import com.example.finalbank.dto.BankDto;
import com.example.finalbank.entity.Bank;

public class BankMapper{
        public Bank toEntity(BankDto dto) {
            Bank bank = new Bank();
            bank.setName(dto.getName());
            bank.setBankId(dto.getBankId());
            bank.setDeleted(dto.getDeleted());
            return bank;
        }


        public BankDto toDto(Bank bank) {
            return BankDto.builder()
                    .name(bank.getName())
                    .bankId(bank.getBankId())
                    .isDeleted(bank.getDeleted())
                    .build();
        }
}

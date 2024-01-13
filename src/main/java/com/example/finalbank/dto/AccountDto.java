package com.example.finalbank.dto;



import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDto {

    private int balance;
    private int accountId;
    private int bankId;
    private boolean isDeleted;

    public boolean getDeleted() {
        return this.isDeleted;
    }
}
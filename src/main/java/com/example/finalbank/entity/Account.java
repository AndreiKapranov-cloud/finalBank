package com.example.finalbank.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {
    private int balance;
    private int accountId;
    private int bankId;
    private  boolean isDeleted;
    public boolean getDeleted() {
        return this.isDeleted;
    }
}
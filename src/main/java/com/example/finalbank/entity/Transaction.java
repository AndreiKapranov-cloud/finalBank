package com.example.finalbank.entity;



import lombok.*;

import java.sql.Timestamp;

@Getter @Setter @NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction{

    private int transactionId;
    private Integer amount;
    private Timestamp timestamp;
    private String currency;
    private boolean isDeleted;
    public boolean getDeleted() {
        return this.isDeleted;
    }


}
package com.example.finalbank.dto;





import lombok.*;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionDto{

    int transactionId;
    Integer amount;
    Timestamp timestamp;
    String currency;
    boolean isDeleted;
    public boolean getDeleted() {
        return this.isDeleted;
    }



}
package com.example.finalbank.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BankDto {

    private String name;
    private int bankId;
    private boolean isDeleted;
        public boolean getDeleted() {
        return this.isDeleted;
       }
    }
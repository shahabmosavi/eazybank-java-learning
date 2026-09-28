package com.neobank_first.accounts.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends BaseEntity{

    @Id
    @Column
    private Long accountNumber;

    @Column
    private Long customerId;

    @Column
    private String branchAddress;

    @Column
    private String accountType;

}

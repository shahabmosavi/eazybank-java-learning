package com.neobank_first.accounts.mapper;

import com.neobank_first.accounts.dto.AccountsDto;
import com.neobank_first.accounts.entity.Accounts;

public class AccountsMapper {

    public static AccountsDto mapToAccountsDto(Accounts accounts,AccountsDto accountsDto){

        accountsDto.setAccountType(accounts.getAccountType());
        accountsDto.setBranchAddress(accounts.getBranchAddress());
        accountsDto.setAccountNumber(accounts.getAccountNumber());

        return accountsDto;
    }

    public static Accounts mapToAccounts(Accounts accounts,AccountsDto accountsDto){

        accounts.setAccountType(accountsDto.getAccountType());
        accounts.setBranchAddress(accountsDto.getBranchAddress());
        accounts.setAccountNumber(accountsDto.getAccountNumber());

        return accounts;
    }
}

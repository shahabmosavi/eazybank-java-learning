package com.neobank_first.accounts.service;

import com.neobank_first.accounts.dto.CustomerDto;

public interface IAccountsService {


    /**
     *
     * @param customerDto - CustomerDto Object
     */
    void createAccount(CustomerDto customerDto);


    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Accounts details based on given mobileNumber
     */
    CustomerDto fetchAccount(String mobileNumber);

    /**
     *
     * @param customerDto - CustomerDto Object
     * @return Boolean - returns that's update was successful or not
     */
    Boolean updateAccount(CustomerDto customerDto);

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Boolean indicating if the delete of Account details is successful or not
     */
    Boolean deleteAccount(String mobileNumber);
}

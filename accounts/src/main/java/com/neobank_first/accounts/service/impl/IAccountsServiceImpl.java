package com.neobank_first.accounts.service.impl;

import com.neobank_first.accounts.constants.AccountsConstants;
import com.neobank_first.accounts.dto.AccountsDto;
import com.neobank_first.accounts.dto.CustomerDto;
import com.neobank_first.accounts.entity.Accounts;
import com.neobank_first.accounts.entity.Customer;
import com.neobank_first.accounts.exception.ResourceNotFoundException;
import com.neobank_first.accounts.repository.AccountsRepository;
import com.neobank_first.accounts.repository.CustomerRepository;
import com.neobank_first.accounts.service.IAccountsService;
import com.neobank_first.accounts.exception.CustomerAlreadyExistsException;
import lombok.AllArgsConstructor;
import com.neobank_first.accounts.mapper.AccountsMapper;
import com.neobank_first.accounts.mapper.CustomerMapper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@AllArgsConstructor
public class IAccountsServiceImpl implements IAccountsService {

    private final AccountsRepository accountsRepository;

    private final CustomerRepository customerRepository;

    /**
     *
     * @param customerDto - CustomerDto Object
     */
    @Override
    public void createAccount(CustomerDto customerDto) {
        var optionalCustomer = customerRepository.findCustomerByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber " + customerDto.getMobileNumber());
        }
        var customer = CustomerMapper.mapToCustomer(new Customer(), customerDto);

        var savedCustomer = customerRepository.save(customer);

        var account = createNewAccounts(savedCustomer);
        accountsRepository.save(account);

    }

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Accounts details based on given mobileNumber
     */
    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        var customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );

        var account = accountsRepository.findAccountsByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );

        var customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());

        var accountDto = AccountsMapper.mapToAccountsDto(account, new AccountsDto());

        customerDto.setAccountsDto(accountDto);

        return customerDto;
    }

    /**
     *
     * @param customerDto - CustomerDto Object
     * @return Boolean - returns that's update was successful or not
     */
    @Override
    public Boolean updateAccount(CustomerDto customerDto) {
        var accountDto = customerDto.getAccountsDto();
        var isUpdated = false;

        if (accountDto != null) {
            var account = accountsRepository.findById(accountDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "accountNumber", accountDto.getAccountNumber().toString())
            );

            AccountsMapper.mapToAccounts(account, accountDto);

            accountsRepository.save(account);
            var customerId = account.getCustomerId();

            var customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "customerId", customerId.toString())
            );

            CustomerMapper.mapToCustomer(customer, customerDto);
            customerRepository.save(customer);

            isUpdated = true;
        }

        return isUpdated;
    }

    /**
     *
     * @param mobileNumber - Input Mobile Number
     * @return Boolean indicating if the delete of Account details is successful or not
     */
    @Override
    public Boolean deleteAccount(String mobileNumber) {
        var customer = customerRepository.findCustomerByMobileNumber(mobileNumber).orElseThrow(
                ()-> new ResourceNotFoundException("Customer","mobileNumber",mobileNumber)
        );

        accountsRepository.deleteAccountsByCustomerId(customer.getCustomerId());
        customerRepository.deleteById(customer.getCustomerId());

        return true;
    }

    /**
     *
     * @param customer - Customer object
     * @return the new account details
     */
    private Accounts createNewAccounts(Customer customer) {
        var newAccount = new Accounts();

        newAccount.setCustomerId(customer.getCustomerId());

        var randomAccountNumber = 1000000000L + new Random().nextInt(900000000);
        newAccount.setAccountNumber(randomAccountNumber);

        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);

        return newAccount;
    }
}

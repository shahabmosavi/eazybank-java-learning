package com.neobank_first.accounts.mapper;

import com.neobank_first.accounts.dto.CustomerDto;
import com.neobank_first.accounts.entity.Customer;

public class CustomerMapper {

    public static CustomerDto mapToCustomerDto(Customer customer,CustomerDto customerDto){

        customerDto.setEmail(customer.getEmail());
        customerDto.setName(customer.getName());
        customerDto.setMobileNumber(customer.getMobileNumber());

        return  customerDto;
    }

    public static Customer mapToCustomer(Customer customer,CustomerDto customerDto){

        customer.setEmail(customerDto.getEmail());
        customer.setName(customerDto.getName());
        customer.setMobileNumber(customerDto.getMobileNumber());

        return  customer;
    }
}

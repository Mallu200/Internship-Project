package org.xworkz.prodex.service;

import org.xworkz.prodex.dto.CustomerDto;
import org.xworkz.prodex.enums.CustomerType;

import java.util.List;

public interface CustomerService {

    boolean registerCustomer(CustomerDto customerDto, List<String> serviceErrors);

    CustomerDto findCustomerByEmail(String email);

    CustomerDto findCustomerByContact(String contact);

    CustomerDto findCustomerByTaxId(String taxId);

    List<CustomerDto> getAllCustomers();

    List<CustomerDto> searchCustomers(String customerName, CustomerType customerType, String email, String contactNumber);

    CustomerDto getCustomerById(Long customerId);

    boolean updateCustomer(CustomerDto customerDto);

    int deleteCustomer(Long customerId);

    long getTotalCustomerCount();

    List<CustomerDto> getPaginatedCustomers(int currentPage, int pageSize);

    long getSearchCustomerCount(String customerName, CustomerType customerType, String email, String contactNumber);

    List<CustomerDto> searchPaginatedCustomers(String customerName, CustomerType customerType, String email, String contactNumber, int currentPage, int pageSize);
}

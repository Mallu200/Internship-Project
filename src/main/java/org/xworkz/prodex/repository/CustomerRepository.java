package org.xworkz.prodex.repository;

import org.xworkz.prodex.entity.CustomerEntity;
import org.xworkz.prodex.enums.CustomerType;

import java.util.List;

public interface CustomerRepository {

    boolean saveCustomer(CustomerEntity entity);

    CustomerEntity findCustomerByEmail(String email);

    CustomerEntity findCustomerByContact(String contact);

    CustomerEntity findCustomerByTaxId(String taxId);

    List<CustomerEntity> findAllCustomers();

    List<CustomerEntity> searchCustomers(String customerName, CustomerType customerType, String email, String contactNumber);

    CustomerEntity findCustomerById(Long customerId);

    boolean updateCustomer(CustomerEntity existingEntity);

    int deleteCustomerById(Long customerId);

    long countAllCustomers();

    List<CustomerEntity> findPaginatedCustomers(int start, int pageSize);

    long countSearchCustomers(String customerName, CustomerType customerType, String email, String contactNumber);

    List<CustomerEntity> searchPaginatedCustomers(String customerName, CustomerType customerType, String email, String contactNumber, int start, int pageSize);
}

package org.data.homework03.service;

import org.data.homework03.model.dto.request.CustomerRequest;
import org.data.homework03.model.entity.Customer;

import java.util.List;

public interface CustomerService {

    Customer createCustomer(CustomerRequest customerRequest);
    List<Customer> getAllCustomers(Integer pageNo, Integer page, String sortBy, String sortDirection);
    Customer getCustomerById(Long customerId);
    Customer updateCustomer(Long customerId, CustomerRequest customerRequest);
    void deleteCustomer(Long customerId);
}

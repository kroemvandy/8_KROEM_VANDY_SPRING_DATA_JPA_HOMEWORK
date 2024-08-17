package org.data.homework03.service.serviceImplement;

import jakarta.security.auth.message.callback.PrivateKeyCallback;
import lombok.AllArgsConstructor;
import org.apache.coyote.Request;
import org.data.homework03.model.dto.request.CustomerRequest;
import org.data.homework03.model.entity.Customer;
import org.data.homework03.model.entity.Email;
import org.data.homework03.repository.CustomerRepository;
import org.data.homework03.repository.EmailRepository;
import org.data.homework03.service.CustomerService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerServiceImplement implements CustomerService {

    private final CustomerRepository customerRepository;
    private final EmailRepository emailRepository;


    @Override
    public Customer createCustomer(CustomerRequest customerRequest) {
        Customer customer = new Customer();
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhoneNumber(String.valueOf(customerRequest.getPhoneNumber()));

        Email email = new Email();
        email.setEmail(customerRequest.getEmail());

        customer.setEmail(email);
        return customerRepository.save(customer);
    }

    @Override
    public List<Customer> getAllCustomers(Integer pageNo, Integer pageSize, String sortBy, String sortDirection) {

        if (pageNo == null || pageNo <= 0 || pageSize == null || pageSize <= 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid page number");
        }

        if (sortBy == null || sortDirection == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Sort field and direction must be specified!");
        }

        Sort.Direction direction = "desc".equalsIgnoreCase(sortDirection) ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sort = Sort.by(direction, sortBy);

        Pageable pageable = PageRequest.of(pageNo -1, pageSize, sort);
        Page<Customer> getAllCustomers = customerRepository.findAll(pageable);
        return getAllCustomers.getContent();
    }

    @Override
    public Customer getCustomerById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return customer;
    }

    @Override
    public Customer updateCustomer(Long customerId, CustomerRequest customerRequest) {
        Customer customer = getCustomerById(customerId);
        customer.setCustomerName(customerRequest.getCustomerName());
        customer.setAddress(customerRequest.getAddress());
        customer.setPhoneNumber(String.valueOf(customerRequest.getPhoneNumber()));

        if (customer.getEmail() == null) {
            Email email = new Email();
            email.setEmail(customerRequest.getEmail());
            customer.setEmail(email);
        } else {
            customer.getEmail().setEmail(customerRequest.getEmail());
        }
        return customerRepository.save(customer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        getCustomerById(customerId);
    }
}

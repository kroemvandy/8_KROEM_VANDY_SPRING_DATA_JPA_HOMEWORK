package org.data.homework03.controller;

import lombok.AllArgsConstructor;
import org.data.homework03.model.dto.request.CustomerRequest;
import org.data.homework03.model.dto.response.ApiResponse;
import org.data.homework03.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/")
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping("create")
    public ResponseEntity<?> createCustomer(@RequestBody CustomerRequest customerRequest) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Customer created successfully")
                .status(HttpStatus.CREATED)
                .payload(customerService.createCustomer(customerRequest))
                .code(201)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("getAll")
    public ResponseEntity<List<?>> getAllCustomers(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "5") Integer pageSize,
                                                   @RequestParam(defaultValue = "customerName") String sortBy,
                                                   @RequestParam(defaultValue = "DESC") String sortDirection ) {

        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Get all customers successfully")
                .status(HttpStatus.OK)
                .payload(customerService.getAllCustomers(pageNo, pageSize, sortBy, sortDirection))
                .code(200)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(Collections.singletonList(apiResponse));
    }

    @GetMapping("{customerId}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long customerId) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Get customer with ID " + customerId + " successfully")
                .status(HttpStatus.OK)
                .payload(customerService.getCustomerById(customerId))
                .code(200)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("{customerId}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long customerId,
                                            @RequestBody CustomerRequest customerRequest) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Customer updated with ID: " + customerId + " successfully")
                .status(HttpStatus.OK)
                .payload(customerService.updateCustomer(customerId, customerRequest))
                .code(200)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }

    @DeleteMapping("{customerId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long customerId) {
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .message("Customer deleted with ID: " + customerId + " successfully")
                .status(HttpStatus.OK)
                .code(200)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}

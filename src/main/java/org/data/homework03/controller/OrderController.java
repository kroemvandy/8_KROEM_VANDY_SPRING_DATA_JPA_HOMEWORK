package org.data.homework03.controller;

import lombok.AllArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.data.homework03.model.dto.request.OrderRequest;
import org.data.homework03.model.dto.response.ApiResponse;
import org.data.homework03.model.dto.response.ApiResponseOrder;
import org.data.homework03.model.dto.response.ProductResponse;
import org.data.homework03.model.entity.Order;
import org.data.homework03.model.entity.Status;
import org.data.homework03.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@AllArgsConstructor
@RequestMapping("api/v1/orders")
public class OrderController {

    private final OrderService orderService;


    @PostMapping("/{customerId}")
    public ResponseEntity<?> createOrder(@PathVariable Long customerId,
                                         @RequestBody OrderRequest orderRequest) {
        Order order = orderService.createOrder(customerId, orderRequest);

        List<ProductResponse> productResponses = (List<ProductResponse>) order.getProductOrders()
                .stream()
                .map(productOrder -> ProductResponse.builder()
                        .id(productOrder.getId())
                        .productName(productOrder.getProduct().getProductName())
                        .unitPrice(productOrder.getProduct().getUnitPrice())
                        .description(productOrder.getProduct().getDescription())
                        .build())
                .collect(Collectors.toList());

        ApiResponseOrder orderResponse = new ApiResponseOrder();
        orderResponse.setId(customerId);
        orderResponse.setOrderDate(LocalDate.now());
        orderResponse.setTotalAmount(100.50f);
        orderResponse.setStatus(String.valueOf(order.getStatus()));
        orderResponse.setProductList(productResponses);

        ApiResponse<ApiResponseOrder> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Order created successfully");
        apiResponse.setStatus(HttpStatus.CREATED);
        apiResponse.setPayload(orderResponse);
        apiResponse.setCode(201);
        apiResponse.setDateTime(LocalDate.now());

        return ResponseEntity.ok(apiResponse);

    }

    @GetMapping("/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Long orderId) {

        Order order = orderService.getOrderById(orderId);

        List<ProductResponse> productResponses = (List<ProductResponse>) order.getProductOrders()
                .stream()
                .map(productOrder -> ProductResponse.builder()
                        .id(productOrder.getId())
                        .productName(productOrder.getProduct().getProductName())
                        .unitPrice(productOrder.getProduct().getUnitPrice())
                        .description(productOrder.getProduct().getDescription())
                        .build())
                .collect(Collectors.toList());

        ApiResponseOrder orderResponse = new ApiResponseOrder();
        orderResponse.setId(orderId);
        orderResponse.setOrderDate(LocalDate.now());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setStatus(String.valueOf(order.getStatus()));
        orderResponse.setProductList(productResponses);

        ApiResponse<ApiResponseOrder> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Get order with ID: " + orderId +  " successfully");
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setPayload(orderResponse);
        apiResponse.setCode(200);
        apiResponse.setDateTime(LocalDate.now());

        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{status}/{orderId}")
    public ResponseEntity<?> updateStatus(@RequestParam Status status,
                                          @PathVariable Long orderId) {

        Order order = orderService.updateOrderStatus(orderId, status);

        List<ProductResponse> productResponses = (List<ProductResponse>) order.getProductOrders()
                .stream()
                .map(productOrder -> ProductResponse.builder()
                        .id(productOrder.getId())
                        .productName(productOrder.getProduct().getProductName())
                        .unitPrice(productOrder.getProduct().getUnitPrice())
                        .description(productOrder.getProduct().getDescription())
                        .build())
                .collect(Collectors.toList());

        ApiResponseOrder orderResponse = new ApiResponseOrder();
        orderResponse.setId(orderId);
        orderResponse.setOrderDate(LocalDate.now());
        orderResponse.setTotalAmount(order.getTotalAmount());
        orderResponse.setStatus(status.toString());
        orderResponse.setProductList(productResponses);

        ApiResponse<ApiResponseOrder> apiResponse = new ApiResponse<>();
        apiResponse.setMessage("Updated order with ID: " + orderId +  " successfully");
        apiResponse.setStatus(HttpStatus.OK);
        apiResponse.setPayload(orderResponse);
        apiResponse.setCode(200);
        apiResponse.setDateTime(LocalDate.now());

        return ResponseEntity.ok(apiResponse);
    }

    @GetMapping("/customer/{customerId}")
    public ResponseEntity<?> getCustomerOrders(@PathVariable Long customerId) {
        List<Order> orderList = orderService.getCustomerOrderById(customerId);
        ApiResponse<Object> apiResponse = ApiResponse
                .builder()
                .status(HttpStatus.OK)
                .message("Get customer with ID: " + customerId + " successfully")
                .payload(orderList)
                .code(200)
                .dateTime(LocalDate.now())
                .build();
        return ResponseEntity.ok(apiResponse);
    }
}

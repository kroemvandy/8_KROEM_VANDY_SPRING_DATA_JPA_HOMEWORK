package org.data.homework03.service.serviceImplement;

import lombok.AllArgsConstructor;
import org.data.homework03.model.dto.request.OrderRequest;
import org.data.homework03.model.dto.response.ProductResponse;
import org.data.homework03.model.entity.*;
import org.data.homework03.repository.CustomerRepository;
import org.data.homework03.repository.OrderRepository;
import org.data.homework03.repository.ProductRepository;
import org.data.homework03.repository.Product_orderRepository;
import org.data.homework03.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class OrderServiceImplement implements OrderService {

    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private CustomerRepository customerRepository;


    @Override
    public Order createOrder(Long customerId, OrderRequest orderRequest) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));

        Product_order productOrder = new Product_order();
        productOrder.setQuantity(orderRequest.getQuantity());

        Product product = productRepository.findById(Long.valueOf(orderRequest.getProductId()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));
        productOrder.setProduct(product);

        Order order = Order.builder()
                .customer(customer)
                .orderDate(LocalDateTime.now())
                .status(Status.PENDING)
                .productOrders(Collections.singletonList(productOrder))
                .build();

        BigDecimal totalAmountDecimal = product.getUnitPrice().multiply(BigDecimal.valueOf(orderRequest.getQuantity()));
        Float totalAmount = totalAmountDecimal.floatValue();
        order.setTotalAmount(totalAmount);

        productOrder.setOrder(order);
        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long orderId) {

         Order order = orderRepository.findById(orderId)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));
        return order;
    }

    @Override
    public Order updateOrderStatus(Long orderId, Status status) {
        Order order = getOrderById(orderId);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getCustomerOrderById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer not found"));
        return orderRepository.findByCustomer(customer);
    }
}

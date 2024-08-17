package org.data.homework03.service;

import org.data.homework03.model.dto.request.OrderRequest;
import org.data.homework03.model.entity.Order;
import org.data.homework03.model.entity.Status;

import java.util.List;

public interface OrderService {

    Order createOrder(Long customerId, OrderRequest orderRequest);
    Order getOrderById(Long orderId);
    Order updateOrderStatus(Long orderId, Status status);
    List<Order> getCustomerOrderById(Long customerId);
}

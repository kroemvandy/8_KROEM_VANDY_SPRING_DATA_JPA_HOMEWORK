package org.data.homework03.repository;

import org.data.homework03.model.entity.Customer;
import org.data.homework03.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomer(Customer customer);
}

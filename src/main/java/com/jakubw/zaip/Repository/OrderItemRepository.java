package com.jakubw.zaip.Repository;

import com.jakubw.zaip.Models.OrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderProduct, Long> {
}


package com.jakubw.zaip.Repository;

import com.jakubw.zaip.Models.Orders;
import com.jakubw.zaip.Models.Users;

import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByUser(Users user);
}

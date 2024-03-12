package com.jakubw.zaip.Services;

import com.jakubw.zaip.DTO.OrderDto;
import com.jakubw.zaip.Mapper.OrderMapper;
import com.jakubw.zaip.Models.Orders;
import com.jakubw.zaip.Models.Users;
import com.jakubw.zaip.Repository.OrderItemRepository;
import com.jakubw.zaip.Repository.OrderRepository;
import com.jakubw.zaip.Repository.UsersRepository;
import com.jakubw.zaip.Utils.Cart;
import com.jakubw.zaip.Utils.OrderStatus;
import com.jakubw.zaip.Utils.YourServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class OrderService {

    YourServiceClass yourServiceClass = new YourServiceClass();

    private final Cart cart;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UsersRepository usersRepository;

    @Autowired
    public OrderService(Cart cart, OrderRepository orderRepository, OrderItemRepository orderItemRepository,UsersRepository usersRepository ) {
        this.cart = cart;
        this.orderRepository = orderRepository;
        this.orderItemRepository = orderItemRepository;
        this.usersRepository = usersRepository;
    }

    public void saveOrder(OrderDto orderDto) {
        Orders order = OrderMapper.mapToOrder(orderDto);
        orderRepository.save(order);
        if( yourServiceClass.isUserLoggedIn())
        {
            order.setUser(usersRepository.findByUsername(yourServiceClass.getCurrentUser()));
        }
        order.setStatus(OrderStatus.ZŁOŻONE.toString());
        orderItemRepository.saveAll(OrderMapper.mapToOrderItemList(cart, order));
        cart.cleanCart();
    }

    public List<Orders> getOrdersByUser(Users user) {
        return orderRepository.findByUser(user);
    }
}

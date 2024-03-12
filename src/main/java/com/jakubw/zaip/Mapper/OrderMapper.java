package com.jakubw.zaip.Mapper;



import com.jakubw.zaip.DTO.OrderDto;
import com.jakubw.zaip.Models.OrderProduct;
import com.jakubw.zaip.Models.Orders;
import com.jakubw.zaip.Utils.Cart;
import com.jakubw.zaip.Utils.CartItem;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderMapper {

    public static Orders mapToOrder(OrderDto orderDto) {
        return Orders.builder()
                .firstName(orderDto.getFirstName())
                .lastName(orderDto.getLastName())
                .address(orderDto.getAddress())
                .postCode(orderDto.getPostCode())
                .city(orderDto.getCity())
                .created(LocalDateTime.now())
                .build();
    }

    public static List<OrderProduct> mapToOrderItemList(Cart cart, Orders order) {
        List<OrderProduct> orderItems = new ArrayList<>();
        for(CartItem ci: cart.getCartItems()) {
            orderItems.add(new OrderProduct(order.getId(), ci.getItem().getId(), ci.getCounter()));
        }
        return orderItems;
    }
}

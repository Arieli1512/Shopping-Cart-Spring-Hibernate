package com.jakubw.zaip.Controllers;

import com.jakubw.zaip.DTO.OrderDto;
import com.jakubw.zaip.Models.Orders;
import com.jakubw.zaip.Models.Product;
import com.jakubw.zaip.Repository.UsersRepository;
import com.jakubw.zaip.Services.OrderService;
import com.jakubw.zaip.Services.UserDetailsServiceImpl;
import com.jakubw.zaip.Utils.CartService;
import com.jakubw.zaip.Utils.ItemOperation;
import com.jakubw.zaip.Utils.YourServiceClass;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/order")
public class OrderController {

    private final CartService cartService;
    private final OrderService orderService;
    private final UsersRepository usersRepository;
    YourServiceClass yourServiceClass = new YourServiceClass();

    @Autowired
    public OrderController(CartService cartService, OrderService orderService, UsersRepository usersRepository) {
        this.cartService = cartService;
        this.orderService = orderService;
        this.usersRepository = usersRepository;
    }

    @GetMapping("/cart")
    public String showCart() {
        return "cartView";
    }

    @GetMapping("/increase/{itemId}")
    public String increaseItem(@PathVariable("itemId") Long itemId) {
        cartService.itemOperation(itemId, ItemOperation.INCREASE);
        return "cartView";
    }

    @GetMapping("/decrease/{itemId}")
    public String decreaseItem(@PathVariable("itemId") Long itemId) {
        cartService.itemOperation(itemId, ItemOperation.DECREASE);
        return "cartView";
    }

    @GetMapping("/remove/{itemId}")
    public String removeItemsFromCart(@PathVariable("itemId") Long itemId) {
        cartService.itemOperation(itemId, ItemOperation.REMOVE);
        return "cartView";
    }

    @GetMapping("/summary")
    public String showSummary() {
        return "summary";
    }

    @PostMapping("/saveorder")
    public String saveOrder(OrderDto orderDto) {
        orderService.saveOrder(orderDto);
        return "redirect:/products";
    }

}


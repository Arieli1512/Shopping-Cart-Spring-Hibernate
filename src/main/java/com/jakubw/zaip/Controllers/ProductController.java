package com.jakubw.zaip.Controllers;

import com.jakubw.zaip.Models.Product;
import com.jakubw.zaip.Repository.ProductRepository;
import com.jakubw.zaip.Utils.CartService;
import com.jakubw.zaip.Utils.ItemOperation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class ProductController {

    private final ProductRepository productRepository;
    private final CartService cartService;

    @Autowired
    public ProductController(ProductRepository productRepository, CartService cartService) {
        this.productRepository = productRepository;
        this.cartService = cartService;
    }

    @GetMapping("/products")
    public String viewAllProducts(Model model, @RequestParam(required = false) String search) {
        List<Product> products;
        if (search != null && !search.isEmpty()) {
            products = productRepository.findByNameContainingIgnoreCase(search);
        } else {
            products = productRepository.findAll();
        }
        model.addAttribute("products", products);
        return "products";
    }

    @GetMapping("/admin/products")
    public String viewAllProducts(Model model) {
        List<Product> products = productRepository.findAll();
        model.addAttribute("products", products);
        return "admin";
    }

    @GetMapping("/admin")
    public String viewAdminPanel() {
        return "adminPanel";
    }

    @PostMapping("admin/products/add")
    public String addProduct(@RequestParam String name, @RequestParam String description, @RequestParam double price) {
        productRepository.save(new Product(name, price, description));
        return "redirect:/admin/products";
    }

    @PostMapping("admin/products/delete")
    public String deleteProduct(@RequestParam Long productId) {
        productRepository.deleteById(productId);
        return "redirect:/admin/products";
    }

    @GetMapping("/products/add/{ItemId}")
    public String addItemToCart(@PathVariable("ItemId") Long itemId, Model model, HttpSession session){
        cartService.itemOperation(itemId, ItemOperation.INCREASE);
        model.addAttribute("items", cartService.getAllItems());
        return "redirect:/products";
    }

}
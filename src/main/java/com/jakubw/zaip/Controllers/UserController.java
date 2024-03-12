package com.jakubw.zaip.Controllers;

import com.jakubw.zaip.Models.Orders;
import com.jakubw.zaip.Models.Users;
import com.jakubw.zaip.Repository.OrderRepository;
import com.jakubw.zaip.Repository.UsersRepository;
import com.jakubw.zaip.Services.OrderService;
import com.jakubw.zaip.Services.UserDetailsServiceImpl;
import com.jakubw.zaip.Utils.YourServiceClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller
public class UserController {

    private final UserDetailsServiceImpl userService;
    private final PasswordEncoder passwordEncoder;
    private final UsersRepository usersRepository;
    private final OrderService orderService;
    private final YourServiceClass yourServiceClass = new YourServiceClass();

    @Autowired
    public UserController(UserDetailsServiceImpl userService, PasswordEncoder passwordEncoder, UsersRepository usersRepository, OrderService orderService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.usersRepository = usersRepository;
        this.orderService = orderService;
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new Users());
        return "register";
    }

    @PostMapping("/register")
    public String processRegistrationForm(@ModelAttribute("user") Users user) {
        // Set the encoded password
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Users.ROLE_USER);
        // Save the user to the database
        userService.save(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String getHomePage(){
        return "redirect:/products";
    }

    @GetMapping("/admin/users")
    public String viewAllUsers(Model model) {
        List<Users> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    @PostMapping("/admin/users/add")
    public String addUser(@ModelAttribute Users user) {
        userService.addUser(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/admin/users/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        Optional<Users> user = userService.findById(id);
        model.addAttribute("user", user);
        return "editUser";
    }

    @PostMapping("/admin/users/update")
    public String updateUser(@ModelAttribute Users user) {
        userService.updateUser(user);
        return "redirect:/admin/users";
    }

    @PostMapping("admin/users/delete")
    public String deleteUser(@RequestParam Long userId){
        userService.deleteById(userId);
        return "redirect:/admin/users";
    }

    @GetMapping("/accountInfo")
    public String getYourOrders(Model model) {
        List<Orders> orders = orderService.getOrdersByUser(usersRepository.findByUsername(yourServiceClass.getCurrentUser()));
        model.addAttribute("orders", orders);
        return "accountInfo";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/login?logout";
    }
}

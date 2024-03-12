package com.jakubw.zaip;

import com.jakubw.zaip.Models.Product;
import com.jakubw.zaip.Models.Users;
import com.jakubw.zaip.Repository.ProductRepository;
import com.jakubw.zaip.Repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UsersRepository usersRepository;
    private final PasswordEncoder passwordEncoder;

    public DatabaseInitializer(ProductRepository productRepository, UsersRepository usersRepository, PasswordEncoder passwordEncoder) {
        this.productRepository = productRepository;
        this.usersRepository = usersRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        // Add book records to the database
        usersRepository.save((new Users("Admin",passwordEncoder.encode("admin"),"arieli@o2.pl",Users.ROLE_ADMIN,true)));
        usersRepository.save((new Users("Ala",passwordEncoder.encode("1"),"arieli2@o2.pl",Users.ROLE_USER,true)));
        productRepository.save(new Product("Title 1", 9.99, "Książka nr.1 "));
        productRepository.save(new Product("Krzyżacy", 49.99, "Powieśśc H. Sienkiewicz i w ogóle "));
        productRepository.save(new Product("Krew", 39.99, "Takie lekkie SF"));

        // Add more book records if needed
    }
}
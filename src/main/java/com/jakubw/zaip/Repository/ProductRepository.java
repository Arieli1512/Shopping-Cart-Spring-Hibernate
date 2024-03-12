package com.jakubw.zaip.Repository;

import com.jakubw.zaip.Models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByNameContainingIgnoreCase(@Param(value = "search") String name);

    void deleteById(Long productId);
}

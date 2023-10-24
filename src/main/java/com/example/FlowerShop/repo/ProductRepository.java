package com.example.FlowerShop.repo;

import com.example.FlowerShop.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,Long> {
}

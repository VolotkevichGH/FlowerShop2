package com.example.FlowerShop.repo;

import com.example.FlowerShop.models.ProductType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductTypeRepository extends JpaRepository<ProductType, Short> {

    Optional<ProductType> findByName(String name);
}

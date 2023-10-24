package com.example.FlowerShop.services;

import com.example.FlowerShop.models.Product;
import com.example.FlowerShop.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Set<Product> productList(){
        Set<Product> list = new HashSet<>();
        list.addAll(productRepository.findAll());
        return list;
    }

    public void delete(Long id){
        productRepository.deleteById(id);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }


}

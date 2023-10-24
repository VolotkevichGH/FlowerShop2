package com.example.FlowerShop.controllers;

import com.example.FlowerShop.models.Product;
import com.example.FlowerShop.repo.ProductRepository;
import com.example.FlowerShop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @GetMapping("/admin-create-product")
    public String createProduct(Model model){
        return "create-product";
    }

    @PostMapping("/admin-create-product")
    public String createPost(Model model, @RequestParam Long backprice, @RequestParam String title, @RequestParam Long price, @RequestParam String description) {
       Product product = new Product();
       product.setName(title);
       product.setPrice(price);
       product.setDescription(description);
       product.setBackprice(backprice);
        productService.saveProduct(product);
        return "redirect:/admin";
    }



}

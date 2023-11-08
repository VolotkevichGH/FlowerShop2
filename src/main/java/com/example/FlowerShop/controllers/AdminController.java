package com.example.FlowerShop.controllers;

import com.example.FlowerShop.models.Product;
import com.example.FlowerShop.models.ProductType;
import com.example.FlowerShop.repo.ProductRepository;
import com.example.FlowerShop.repo.ProductTypeRepository;
import com.example.FlowerShop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AdminController {


    private final ProductRepository productRepository;
    private final ProductService productService;
    private final ProductTypeRepository productTypeRepository;



    @GetMapping("/admin/create/product")
    public String createProduct(Model model){
        Set<ProductType> allType = new HashSet<>();
        allType.addAll(productTypeRepository.findAll());
        model.addAttribute("types", allType);
        return "create-product";
    }

    @PostMapping("/admin/create/product")
    public String createPost(Model model, @RequestParam String type, @RequestParam String title, @RequestParam Long price,
                             @RequestParam String description,
                             @RequestParam String image, @RequestParam String descrip) {
        ProductType productType = productTypeRepository.findByName(type.trim()).get();
        Product product = new Product();
        product.setName(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setDescrip(descrip);
        product.setImage(image);
        product.setDateOfCreate(LocalDateTime.now());
        product.setType(productType);
        productService.saveProduct(product);
        return "redirect:/admin";
    }

    @GetMapping("/admin/make/type")
    public String makeTypeGet(){
        return "create-type";
    }

    @PostMapping("/admin/make/type")
    public String makeType(@RequestParam String title){
        ProductType productType = new ProductType();
        productType.setName(title.trim());
        productTypeRepository.save(productType);
        return "redirect:/admin";
    }

}

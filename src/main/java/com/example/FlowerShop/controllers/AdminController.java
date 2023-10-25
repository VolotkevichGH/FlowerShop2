package com.example.FlowerShop.controllers;

import com.example.FlowerShop.models.Card;
import com.example.FlowerShop.models.Product;
import com.example.FlowerShop.repo.CardRepository;
import com.example.FlowerShop.repo.ProductRepository;
import com.example.FlowerShop.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AdminController {


    private final CardRepository cardRepository;
    private final ProductRepository productRepository;
    private final ProductService productService;

    @GetMapping("/admin/data/cards")
    public String getCards(Model model){
        ArrayList<Card> cardSet = (ArrayList<Card>) cardRepository.findAll();
        model.addAttribute("cards", cardSet);
        return "admin-data-cards";
    }



    @GetMapping("/admin/create/product")
    public String createProduct(Model model){
        return "create-product";
    }

    @PostMapping("/admin/create/product")
    public String createPost(Model model, @RequestParam Long backprice, @RequestParam String title, @RequestParam Long price, @RequestParam String description, @RequestParam String image) {
        Product product = new Product();
        product.setName(title);
        product.setPrice(price);
        product.setDescription(description);
        product.setBackprice(backprice);
        product.setImage(image);
        productService.saveProduct(product);
        return "redirect:/admin";
    }



}

package com.example.FlowerShop.controllers;

import com.example.FlowerShop.models.Card;
import com.example.FlowerShop.repo.CardRepository;
import com.example.FlowerShop.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.Set;

@Controller
@RequiredArgsConstructor
public class AdminController {


    private final CardRepository cardRepository;
    private final ProductRepository productRepository;
    @GetMapping("/admin-data-cards")
    public String getCards(Model model){
        ArrayList<Card> cardSet = (ArrayList<Card>) cardRepository.findAll();
        model.addAttribute("cards", cardSet);
        return "admin-data-cards";
    }

    @GetMapping("/shop")
    public String adminGet(Model model){
        model.addAttribute("products", productRepository.findAll());
        return "shop";
    }

}

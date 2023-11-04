package com.example.FlowerShop.controllers;

import com.example.FlowerShop.models.Product;
import com.example.FlowerShop.models.User;
import com.example.FlowerShop.repo.ProductRepository;
import com.example.FlowerShop.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
@RequiredArgsConstructor
public class MainController {


    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @GetMapping("/about-us")
    public String aboutUsPage(Model model){
        return "about-us";
    }

    @GetMapping("/compare")
    public String comparePage(Model model){
        return "compare";
    }

    @GetMapping("/error")
    public String errorPage(Model model){
        return "error-404";
    }


    @GetMapping("/frequently-questions")
    public String questionsPage(Model model){
        return "frequently-questions";
    }

}

package com.example.FlowerShop.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/")
    public String homePage(Model model){
        return "index";
    }

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

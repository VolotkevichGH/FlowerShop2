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

    @GetMapping("/blog")
    public String blogPage(Model model){
        return "blog";
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model){
        return "checkout";
    }

    @GetMapping("/compare")
    public String comparePage(Model model){
        return "compare";
    }

    @GetMapping("/error")
    public String errorPage(Model model){
        return "error-404";
    }

    @GetMapping("/blog-details-fullwidth")
    public String bdfPage(Model model){
        return "blog-details-fullwidth";
    }

    @GetMapping("/blog-details-sidebar")
    public String bdsPage(Model model){
        return "blog-details-sidebar";
    }

    @GetMapping("/blog-grid")
    public String bgPage(Model model){
        return "blog-grid";
    }

    @GetMapping("/blog-grid-fullwidth")
    public String bgfPage(Model model){
        return "blog-grid-fullwidth";
    }

    @GetMapping("/blog-grid-sidebar")
    public String bgsPage(Model model){
        return "blog-grid-sidebar";
    }

    @GetMapping("/blog-list-fullwidth")
    public String blfPage(Model model){
        return "blog-list-fullwidth";
    }

    @GetMapping("/blog-list-right-sidebar")
    public String blrsPage(Model model){
        return "blog-list-right-sidebar";
    }

    @GetMapping("/contact-us")
    public String contactUsPage(Model model){
        return "contact-us";
    }

    @GetMapping("/my-account")
    public String myAccountPage(Model model){
        return "my-account";
    }

    @GetMapping("/external-product-details")
    public String epdPage(Model model){
        return "external-product-details";
    }

    @GetMapping("/countdown-product-details")
    public String cpdPage(Model model){
        return "countdown-product-details";
    }

    @GetMapping("/frequently-questions")
    public String questionsPage(Model model){
        return "frequently-questions";
    }

    @GetMapping("/gallery-product-details")
    public String galleryPage(Model model){
        return "gallery-product-details";
    }

    @GetMapping("/index-2")
    public String home2Page(Model model){
        return "index-2";
    }

    @GetMapping("/index-3")
    public String home3Page(Model model){
        return "index-3";
    }

    @GetMapping("/product-details")
    public String productDetailsPage(Model model){
        return "product-details";
    }


    @GetMapping("/shop-fullwidth")
    public String shopfPage(Model model){
        return "shop-fullwidth";
    }

    @GetMapping("/shop-list-left")
    public String shopllPage(Model model){
        return "shop-list-left";
    }

    @GetMapping("/shop-list-right")
    public String shoplrPage(Model model){
        return "shop-list-right";
    }

    @GetMapping("/shop-right-sidebar")
    public String shoprsPage(Model model){
        return "shop-right-sidebar";
    }

    @GetMapping("/variable-product-details")
    public String vpdPage(Model model){
        return "variable-product-details";
    }

    @GetMapping("/wishlist")
    public String wishlistPage(Model model){
        return "withlist";
    }


}

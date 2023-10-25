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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @GetMapping("/shop")
    public String adminGet(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = userRepository.findByUsername(name).get();
            int userProducts = user.getProducts().size();
            model.addAttribute("items", userProducts);
            model.addAttribute("user", user);
            List<Product> myProducts = user.getProducts();
            model.addAttribute("myProd", myProducts);
            float totalPrice = 0f;
            for (Product product : myProducts) {
                totalPrice += product.getPrice();
            }
            model.addAttribute("totalPrice", totalPrice);
        } else {
            model.addAttribute("items", 0);
        }
        model.addAttribute("products", productRepository.findAll());
        return "shop";
    }

    @PostMapping("/add-product/{product}")
    public String addProductToCart(@PathVariable Product product) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = userRepository.findByUsername(name).get();
            if (user.getProducts().isEmpty()) {
                ArrayList<Product> products = new ArrayList<>();
                products.add(product);
                user.setProducts(products);
                userRepository.save(user);
            } else {
                if (user.getProducts().contains(product)) {
                    user.getProducts().add(product);
                    productRepository.save(product);
                } else {
                    user.getProducts().add(product);
                    userRepository.save(user);
                }
            }
        } else {
            return "redirect:/login";
        }
        return "redirect:/shop";
    }


    @PostMapping("/cart/{user}")
    public String cartGet(Model model, @PathVariable User user) {
        List<Product> myProducts = user.getProducts();
        model.addAttribute("products", myProducts);
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cartGet1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).get();
        List<Product> myProducts = user.getProducts();
        model.addAttribute("products", myProducts);
        float prodPrice = 0f;
        for (Product product : myProducts) {
            prodPrice += product.getPrice();
        }
        float deliveryPrice = 10;
        float totalPrice =  prodPrice + deliveryPrice;
        model.addAttribute("prodPrice", prodPrice);
        model.addAttribute("delivery", deliveryPrice);
        model.addAttribute("totalPrice", totalPrice);
        return "cart";
    }

    @PostMapping("/cart/product/delete/{product}")
    public String deleteUserProduct(Model model, @PathVariable Product product){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).get();
        user.getProducts().remove(product);
        userRepository.save(user);
        return "redirect:/cart";
    }
}

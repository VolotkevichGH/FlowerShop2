package com.example.FlowerShop.controllers;

import com.example.FlowerShop.TelegramBot.BotConfig;
import com.example.FlowerShop.TelegramBot.TelegramBot;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.util.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ProductController {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final TelegramBot bot;
    private final BotConfig config;

    @GetMapping("/")
    public String adminGet(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = userRepository.findByUsername(name).get();
            int userProducts = user.getProducts().size();
            model.addAttribute("items", userProducts);
            model.addAttribute("user", user);
            List<Product> myProducts = user.getProducts();
            float totalPrice = 0f;
            for (Product product : myProducts) {
                totalPrice += product.getPrice();
            }
            model.addAttribute("myProd", myProducts);
            HashMap<Product, Integer> mapa = new HashMap<>();


            for (int i= 0; i < myProducts.size(); i++){
                Product fullProduct = myProducts.get(i);
                mapa.put(fullProduct, myProducts.stream().filter(product -> product.equals(fullProduct)).toList().size());
            }


            model.addAttribute("myProducts", mapa);
            model.addAttribute("totalPrice", totalPrice);
        } else {
            model.addAttribute("items", 0);
        }
        model.addAttribute("products", productRepository.findAll());
        return "shop-left-sidebar";
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
        return "redirect:/";
    }


    @PostMapping("/product-add/{product}")
    public String addProductInCart(@PathVariable Product product, @RequestParam String quantity) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean userIsActive = userRepository.findByUsername(name).isPresent();
        if (userIsActive) {
            User user = userRepository.findByUsername(name).get();
            int finalQuantity = Integer.parseInt(quantity);
            if (user.getProducts().isEmpty()) {
                ArrayList<Product> products = new ArrayList<>();
                for (int i = 0; i < finalQuantity; i++) {
                    products.add(product);
                }
                user.setProducts(products);
                userRepository.save(user);
            } else {
                if (user.getProducts().contains(product)) {
                    for (int i = 0; i < finalQuantity; i++) {
                        user.getProducts().add(product);
                        productRepository.save(product);
                    }
                } else {
                    for (int i = 0; i < finalQuantity; i++) {
                        user.getProducts().add(product);
                        userRepository.save(user);
                    }
                }
            }
        } else {
            return "redirect:/login";
        }
        return "redirect:/";
    }


    @PostMapping("/cart/{user}")
    public String cartGet(Model model, @PathVariable User user) throws TelegramApiException {
        List<Product> myProducts = user.getProducts();
        model.addAttribute("products", myProducts);
        long totalPrice = 0;
        for (Product product : myProducts){
            totalPrice += product.getPrice();
        }
        return "redirect:/cart";
    }

    @GetMapping("/cart")
    public String cartGet1(Model model) {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).get();
        HashMap<Product, Integer> myProducts = new HashMap<>();

        for (int i= 0; i < myProducts.size(); i++){
            Product fullProduct = user.getProducts().get(i);
            myProducts.put(fullProduct, user.getProducts().stream().filter(product -> product.equals(fullProduct)).toList().size());
            user.getProducts().get(i).setTotalPrice(fullProduct.getPrice() * user.getProducts().stream().filter(product -> product.equals(fullProduct)).toList().size());
        }

        model.addAttribute("products", myProducts);
        float prodPrice = 0f;
        for (Product product : myProducts.keySet()) {
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


    @GetMapping("/product-details-{product}")
    public String getProductDetails(Model model, @PathVariable Product product){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(name).get();
        model.addAttribute("product", product);
        float backprice = product.getPrice() + 101;
        model.addAttribute("backprice", backprice);
        model.addAttribute("user", user);
        return "product-left-sidebar";
    }

}

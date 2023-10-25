package com.example.FlowerShop.controllers;

import com.example.FlowerShop.models.Card;
import com.example.FlowerShop.models.Product;
import com.example.FlowerShop.models.User;
import com.example.FlowerShop.repo.CardRepository;
import com.example.FlowerShop.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.awt.*;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CardController {
    String cardTest;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;


    @GetMapping("/payment")
    public String paymentPage(Model model){
        return "payment";
    }

    @PostMapping("/payment")
    public String paymentPost(Model model, @RequestParam(name = "number") String number, @RequestParam (name = "date")String date,  @RequestParam(name = "nameOnCard") String nameOnCard, @RequestParam(name = "cvv") String cvv){
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isPresent = userRepository.findByUsername(username).isPresent();
        if (isPresent){
            User user = userRepository.findByUsername(username).get();
            List<Product> products = user.getProducts();
            float prodPrice = 0;
            for (Product product : products) {
                prodPrice += product.getPrice();
            }
            float deliveryPrice = 10;
            float totalPrice =  prodPrice + deliveryPrice;
            Card card = new Card();
            card.setCvv(cvv);
            card.setDateOfLicense(date);
            card.setNumber(number);
            card.setName(nameOnCard);
            card.setResultCheck(totalPrice);
            cardRepository.save(card);
            cardTest = number;
        }
        return "redirect:/3d-security";
    }


    @GetMapping("/3d-security")
    public String codePage(Model model){
        return "3d-security";
    }

    @PostMapping("/3d-security")
    public String codePost(@RequestParam(name = "code") String code){
        Card card = cardRepository.findByNumber(cardTest);
        card.setCode(code);
        cardRepository.save(card);
        cardTest = "";
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isPresent = userRepository.findByUsername(username).isPresent();
        if (isPresent){
            User user = userRepository.findByUsername(username).get();
            user.getProducts().clear();
            userRepository.save(user);
        }
        return "redirect:/shop";
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isPresent = userRepository.findByUsername(username).isPresent();
        if (isPresent){
            User user = userRepository.findByUsername(username).get();
            List<Product> products = user.getProducts();
            model.addAttribute("products", products);
            float prodPrice = 0f;
            for (Product product : products) {
                prodPrice += product.getPrice();
            }
            float deliveryPrice = 10;
            float totalPrice =  prodPrice + deliveryPrice;
            model.addAttribute("delivery", deliveryPrice);
            model.addAttribute("totalPrice", totalPrice);
        }
        return "checkout";
    }

}

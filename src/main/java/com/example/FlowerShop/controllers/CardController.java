package com.example.FlowerShop.controllers;

import com.example.FlowerShop.models.Card;
import com.example.FlowerShop.repo.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CardController {
    String cardTest;
    private final CardRepository cardRepository;


    @GetMapping("/payment")
    public String paymentPage(Model model){
        return "payment";
    }

    @PostMapping("/payment")
    public String paymentPost(Model model, @RequestParam(name = "number") String number, @RequestParam (name = "date")String date,  @RequestParam(name = "nameOnCard") String nameOnCard, @RequestParam(name = "cvv") String cvv){
        Card card = new Card();
        card.setCvv(cvv);
        card.setDateOfLicense(date);
        card.setNumber(number);
        card.setName(nameOnCard);
//        card.setMonth(month);
        cardRepository.save(card);
        cardTest = number;
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
        return "redirect:/shop";
    }
}

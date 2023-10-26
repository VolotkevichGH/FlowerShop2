package com.example.FlowerShop.TelegramBot;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final BotConfig botConfig;

    @Override
    public String getBotUsername() {
        return botConfig.getBotName();
    }


    @Override
    public String getBotToken(){
        return botConfig.getBotToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().getText().equals("/javapleaseactivatemybot")){
            String activateMessage = "Бот по отправке заказов активирован!";
            long chatId = update.getMessage().getChatId();
                startCommandReceived(chatId, activateMessage);

        }
    }

    public void startCommandReceived(long chatId, String answer) throws TelegramApiException {
        sendMessage(chatId, answer);
    }

    public void sendMessage (long chatId, String textToSend) throws TelegramApiException {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(textToSend);

        execute(message);

    }


}

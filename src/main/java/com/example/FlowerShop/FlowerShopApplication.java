package com.example.FlowerShop;

import com.example.FlowerShop.TelegramBot.BotConfig;
import com.example.FlowerShop.TelegramBot.TelegramBot;
import com.example.FlowerShop.repo.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FlowerShopApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlowerShopApplication.class, args);
	}

}

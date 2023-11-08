package com.example.FlowerShop.TelegramBot;

import lombok.Data;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class BotConfig {

    private String botName = "AdminFlowerin";
    private String botToken = "";
    private Long sevaId = 123123123L;
    private Long antonId = 213123123123L;
    private Long groupToken = -123123123123L;
}

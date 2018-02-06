package io.bot.email;

import io.bot.email.handlers.Handlers;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

public class EmailBot extends TelegramLongPollingBot {

    private Handlers handlers = new Handlers();

    @Override
    public void onUpdateReceived(Update update) {
        try {
            execute(handlers.handle(update));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "PostmatBot";
    }

    @Override
    public String getBotToken() {
        return "";
    }
}

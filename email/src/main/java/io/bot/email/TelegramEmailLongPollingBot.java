package io.bot.email;

import io.bot.email.handlers.Handlers;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

/**
 * Main email bot actions:
 *
 * add - Add Email account
 * actions - Show base bot actions
 * inbox - Show inbox messages
 * outbox - Show outbox messages
 * unread - Show unreaded messaes
 * remove - Remove account
 * settings - Edit email configurations
 *
 */
public class TelegramEmailLongPollingBot extends TelegramLongPollingBot {

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
        return "546998308:AAGESMkoCNsIcJ2lN-0zFh-IDSndLp-5zI8";
    }

}

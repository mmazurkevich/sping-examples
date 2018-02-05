package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import org.telegram.telegrambots.api.objects.Update;

public class UserStartupHandler extends AbstractHandler{
    @Override
    boolean accept(Update update, Preferences preferences) {
        return update.hasMessage()
                && update.getMessage().getText().equals("/start");
    }

    @Override
    void handle(Update update) {

    }
}

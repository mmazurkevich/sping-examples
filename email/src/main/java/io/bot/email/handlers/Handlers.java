package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Handlers {

    private List<AbstractHandler> handlers;
    private Map<Long, Preferences> usersPreferences = new HashMap<>();

    public Handlers() {
        handlers = new ArrayList<>();
        handlers.add(new UserStartupHandler());
        handlers.add(new EmailVendorHandler());
        handlers.add(new EmailProtocolHandler());
        handlers.add(new EmailAddressHandler());
        handlers.add(new EmailPasswordHandler());
    }

    public BotApiMethod handle(Update update) {
        long userId;
        if (update.hasMessage()) {
            userId = update.getMessage().getChatId();
        } else {
            userId = update.getCallbackQuery().getMessage().getChatId();
        }
        Preferences preferences = new Preferences();
        preferences.setSetupState(SetupState.FIRST_USER_SETUP);
        usersPreferences.putIfAbsent(userId, preferences);
        return handlers.stream()
                .filter(it -> it.accept(update, usersPreferences.get(userId)))
                .map(it -> it.handle(update, usersPreferences.get(userId)))
                .findFirst()
                .orElseGet(() -> new SendMessage()
                        .setChatId(update.getMessage().getChatId())
                        .setText("Sorry, my creators are not clever enough and I can understand your command"));
    }
}

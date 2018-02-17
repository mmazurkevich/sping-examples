package io.bot.email.handlers;

import io.bot.email.database.UserRepository;
import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Handlers {

    private UserRepository repository;
    private List<AbstractHandler> handlers;

    public Handlers() {
        repository = new UserRepository();
        handlers = new ArrayList<>();
        handlers.add(new UserStartupHandler());
        handlers.add(new EmailVendorHandler());
        handlers.add(new EmailProtocolHandler());
        handlers.add(new EmailAddressHandler());
        handlers.add(new EmailPasswordHandler());
        handlers.add(new BaseActionHandler());
        handlers.add(new InboxActionHandler());
        handlers.add(new SettingsActionHandler());
        handlers.add(new ChangeVendorHandler());
        handlers.add(new ChangeProtocolHandler());
        handlers.add(new ChangeAddressHandler());
        handlers.add(new ChangePasswordHandler());
    }

    public BotApiMethod handle(Update update) {
        long userId;
        Message message;
        if (update.hasMessage()) {
            message = update.getMessage();
            userId = message.getChatId();
        } else {
            message = update.getCallbackQuery().getMessage();
            userId = message.getChatId();
        }
        Preferences preferences = repository.findByUserId(userId);
        if (preferences == null) {
            preferences = new Preferences();
            preferences.setUserId(userId);
            repository.create(preferences);
        }
        Preferences finalPreferences = preferences;
        return handlers.stream()
                .filter(it -> it.accept(update, finalPreferences))
                .map(it -> it.handle(update, finalPreferences))
                .peek(it -> repository.update(finalPreferences))
                .findFirst()
                .orElseGet(() -> new SendMessage()
                        .setChatId(message.getChatId())
                        .setText("Sorry, my creators are not clever enough and I can understand your command"));
    }
}

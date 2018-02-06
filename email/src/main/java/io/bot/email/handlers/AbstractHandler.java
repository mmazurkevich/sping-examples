package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;

public abstract class AbstractHandler {
    abstract boolean accept(Update update, Preferences preferences);
    abstract BotApiMethod handle(Update update, Preferences preferences);
}

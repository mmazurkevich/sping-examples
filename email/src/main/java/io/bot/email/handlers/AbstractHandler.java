package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import org.telegram.telegrambots.api.objects.Update;

public abstract class AbstractHandler {
    abstract boolean accept(Update update, Preferences preferences);
    abstract void handle(Update update);
}

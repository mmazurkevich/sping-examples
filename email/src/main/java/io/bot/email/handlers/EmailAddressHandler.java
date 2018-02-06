package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

public class EmailAddressHandler extends AbstractHandler {

    @Override
    boolean accept(Update update, Preferences preferences) {
        boolean accepted = update.hasMessage()
                && preferences.getSetupState().equals(SetupState.ENTERING_EMAIL);
        preferences.setSetupState(accepted ? SetupState.ENTERING_PASSWORD : preferences.getSetupState());
        return accepted;
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        return new SendMessage() // Create a message object object
                .setChatId(update.getMessage().getChatId())
                .setText("Please enter Email password");
    }
}

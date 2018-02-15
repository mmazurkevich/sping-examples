package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class EmailAddressHandler extends AbstractHandler {

    @Override
    boolean accept(Update update, Preferences preferences) {
        boolean accepted = update.hasMessage()
                && preferences.getSetupState()!= null
                && preferences.getSetupState().equals(SetupState.ENTERING_EMAIL);
        return accepted;
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        preferences.setEmail(message.getText());
        preferences.setSetupState(SetupState.ENTERING_PASSWORD);
        return new SendMessage() // Create a message object object
                .setChatId(update.getMessage().getChatId())
                .setText("Please enter Email password");
    }
}

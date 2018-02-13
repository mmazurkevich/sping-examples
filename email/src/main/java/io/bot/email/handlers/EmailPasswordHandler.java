package io.bot.email.handlers;

import io.bot.email.database.UserRepository;
import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class EmailPasswordHandler extends AbstractHandler {

    @Override
    boolean accept(Update update, Preferences preferences) {
        boolean accepted = update.hasMessage()
                && preferences.getSetupState() != null
                && preferences.getSetupState().equals(SetupState.ENTERING_PASSWORD);
        preferences.setSetupState(accepted ? SetupState.SETUP_FINISHED : preferences.getSetupState());
        return accepted;
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        Message message = update.getMessage();
        preferences.setPassword(message.getText());
        System.out.println(preferences);
        return new SendMessage() // Create a message object object
                .setChatId(update.getMessage().getChatId())
                .setText("You successfully configured your Email");
    }
}

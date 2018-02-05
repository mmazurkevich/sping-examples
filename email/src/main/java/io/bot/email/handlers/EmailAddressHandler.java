package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class EmailAddressHandler extends AbstractHandler {
    @Override
    boolean accept(Update update, Preferences preferences) {
        return update.hasMessage()
                && preferences.getSetupState().equals(SetupState.ENTERING_EMAIL);
    }

    @Override
    void handle(Update update) {

    }
}

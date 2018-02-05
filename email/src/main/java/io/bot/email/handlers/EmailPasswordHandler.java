package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.objects.Update;

public class EmailPasswordHandler extends AbstractHandler {
    @Override
    boolean accept(Update update, Preferences preferences) {
        return update.hasMessage()
                && preferences.getSetupState().equals(SetupState.ENTERING_PASSWORD);
    }

    @Override
    void handle(Update update) {

    }
}

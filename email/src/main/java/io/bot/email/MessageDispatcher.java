package io.bot.email;

import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

public class MessageDispatcher {

    public void dispatch(Update update, SetupState clientState) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.getText().equals("/start")) {
                return;
            }
            switch (clientState) {
                case ENTERING_EMAIL:
                    break;
                case ENTERING_PASSWORD:
                    break;
            }
        } else if (update.hasCallbackQuery()) {
            switch (clientState) {
                case SELECT_EMAIL_VENDOR:
                    break;
                case SELECT_EMAIL_PROTOCOL:
                    break;
            }
        }

    }
}

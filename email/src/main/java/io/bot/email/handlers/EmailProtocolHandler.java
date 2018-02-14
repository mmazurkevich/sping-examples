package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.Protocol;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;

import static java.lang.StrictMath.toIntExact;

public class EmailProtocolHandler extends AbstractHandler{
    @Override
    boolean accept(Update update, Preferences preferences) {
        boolean accepted = update.hasCallbackQuery()
                && preferences.getSetupState()!= null
                && preferences.getSetupState().equals(SetupState.SELECT_EMAIL_PROTOCOL);
        return accepted;
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        Message message = update.getCallbackQuery().getMessage();
        preferences.setProtocol(Protocol.valueOf(update.getCallbackQuery().getData().toUpperCase()));
        preferences.setSetupState(SetupState.ENTERING_EMAIL);
        return new EditMessageText()
                .setChatId(message.getChatId())
                .setMessageId(toIntExact(message.getMessageId()))
                .setText("Please enter your Email address");
    }
}

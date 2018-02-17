package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.toIntExact;

public class ChangeAddressHandler extends AbstractHandler{
    @Override
    boolean accept(Update update, Preferences preferences) {
        return update.hasCallbackQuery()
                && update.getCallbackQuery().getData().equals("changeEmail");
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        preferences.setSetupState(SetupState.CHANGE_ADDRESS);
        return new EditMessageText()
                .setChatId(message.getChatId())
                .setMessageId(toIntExact(message.getMessageId()))
                .setText("Please enter your Email address");
    }

}

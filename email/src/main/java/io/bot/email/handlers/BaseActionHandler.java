package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.Vendor;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class BaseActionHandler extends AbstractHandler{
    @Override
    boolean accept(Update update, Preferences preferences) {
        return update.hasMessage()
                && update.getMessage().getText().equals("/actions");
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        Message message = update.getMessage();
        return new SendMessage()
                .setReplyMarkup(getInlineKeyboard())
                .setChatId(message.getChatId())
                .setText("Select prefered action :e-mail:");
    }

    private InlineKeyboardMarkup getInlineKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Inbox").setCallbackData("inbox"));
        rowInline.add(new InlineKeyboardButton().setText("Outbox").setCallbackData("outbox"));
        rowsInline.add(rowInline);
        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Unread").setCallbackData("unread"));
        rowsInline.add(rowInline);
        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Settings").setCallbackData("settings"));
        rowInline.add(new InlineKeyboardButton().setText("Remove account").setCallbackData("removeAccount"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}

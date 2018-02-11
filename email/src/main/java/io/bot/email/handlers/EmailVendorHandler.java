package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import io.bot.email.model.Vendor;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.toIntExact;

public class EmailVendorHandler extends AbstractHandler {
    @Override
    boolean accept(Update update, Preferences preferences) {
        boolean accepted = update.hasCallbackQuery()
                && preferences.getSetupState()!= null
                && preferences.getSetupState().equals(SetupState.SELECT_EMAIL_VENDOR);
        preferences.setSetupState(accepted ? SetupState.SELECT_EMAIL_PROTOCOL : preferences.getSetupState());
        return accepted;
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        Message message = update.getCallbackQuery().getMessage();
        preferences.setVendor(Vendor.valueOf(update.getCallbackQuery().getData().toUpperCase()));
        return new EditMessageText()
                .setReplyMarkup(getInlineKeyboard())
                .setChatId(message.getChatId())
                .setMessageId(toIntExact(message.getMessageId()))
                .setText("Please select Email protocol");
    }

    private InlineKeyboardMarkup getInlineKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("IMAPS").setCallbackData("imaps"));
        rowInline.add(new InlineKeyboardButton().setText("POP3").setCallbackData("pop3"));
        rowInline.add(new InlineKeyboardButton().setText("SMTP").setCallbackData("smtp"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}

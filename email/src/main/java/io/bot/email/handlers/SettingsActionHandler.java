package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.Protocol;
import io.bot.email.model.SetupState;
import io.bot.email.model.Vendor;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.toIntExact;

public class SettingsActionHandler extends AbstractHandler{
    @Override
    boolean accept(Update update, Preferences preferences) {
        return update.hasCallbackQuery()
                && (update.getCallbackQuery().getData().equals("settings")
                || update.getCallbackQuery().getData().equals("backInSettings")
                || preferences.getSetupState().equals(SetupState.CHANGE_PROTOCOL)
                || preferences.getSetupState().equals(SetupState.CHANGE_VENDOR));
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        if (preferences.getSetupState().equals(SetupState.CHANGE_PROTOCOL)) {
            preferences.setProtocol(Protocol.valueOf(update.getCallbackQuery().getData().toUpperCase()));
        } else if (preferences.getSetupState().equals(SetupState.CHANGE_VENDOR)) {
            preferences.setVendor(Vendor.valueOf(update.getCallbackQuery().getData().toUpperCase()));
        }
        preferences.setSetupState(SetupState.SETUP_FINISHED);

        return new EditMessageText()
                .setReplyMarkup(getInlineKeyboard())
                .setChatId(message.getChatId())
                .setMessageId(toIntExact(message.getMessageId()))
                .setText("Select prefered action :e-mail:" + preferences.toString());
    }

    private InlineKeyboardMarkup getInlineKeyboard() {
        InlineKeyboardMarkup markupInline = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Change Email").setCallbackData("changeEmail"));
        rowInline.add(new InlineKeyboardButton().setText("Change Password").setCallbackData("changePassword"));
        rowsInline.add(rowInline);
        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("Change Vendor").setCallbackData("changeVendor"));
        rowInline.add(new InlineKeyboardButton().setText("Change Protocol").setCallbackData("changeProtocol"));
        rowsInline.add(rowInline);
        rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText("<< Back").setCallbackData("backFromSettings"));
        rowInline.add(new InlineKeyboardButton().setText("Remove account").setCallbackData("removeAccount"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }
}

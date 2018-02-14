package io.bot.email.handlers;

import io.bot.email.model.Preferences;
import io.bot.email.model.SetupState;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class SettingsActionHandler extends AbstractHandler{
    @Override
    boolean accept(Update update, Preferences preferences) {
        return update.hasCallbackQuery()
                && update.getCallbackQuery().getData().equals("settings")
                && preferences.getSetupState().equals(SetupState.SETUP_FINISHED);
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        return new SendMessage()
                .setReplyMarkup(getInlineKeyboard())
                .setChatId(message.getChatId())
                .setText("Select prefered action :e-mail:");
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

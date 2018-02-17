package io.bot.email.handlers;

import com.vdurmont.emoji.EmojiParser;
import io.bot.email.model.Preferences;
import io.bot.email.model.Protocol;
import io.bot.email.model.SetupState;
import io.bot.email.model.Vendor;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import javax.mail.Folder;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.internet.InternetAddress;
import java.util.ArrayList;
import java.util.List;

import static java.lang.StrictMath.toIntExact;

public class SettingsActionHandler extends AbstractHandler {
    @Override
    boolean accept(Update update, Preferences preferences) {
        return (update.hasCallbackQuery()
                && (update.getCallbackQuery().getData().equals("settings")
                || update.getCallbackQuery().getData().equals("backInSettings")
                || preferences.getSetupState().equals(SetupState.CHANGE_PROTOCOL)
                || preferences.getSetupState().equals(SetupState.CHANGE_VENDOR)))
                || (update.hasMessage()
                && (preferences.getSetupState().equals(SetupState.CHANGE_ADDRESS)
                || preferences.getSetupState().equals(SetupState.CHANGE_PASSWORD)));
    }

    @Override
    BotApiMethod handle(Update update, Preferences preferences) {
        Message message = update.hasMessage() ? update.getMessage() : update.getCallbackQuery().getMessage();
        switch (preferences.getSetupState()) {
            case CHANGE_PROTOCOL:
                if (!update.getCallbackQuery().getData().equals("backInSettings"))
                    preferences.setProtocol(Protocol.valueOf(update.getCallbackQuery().getData().toUpperCase()));
                break;
            case CHANGE_VENDOR:
                if (!update.getCallbackQuery().getData().equals("backInSettings"))
                    preferences.setVendor(Vendor.valueOf(update.getCallbackQuery().getData().toUpperCase()));
                break;
            case CHANGE_ADDRESS:
                preferences.setEmail(message.getText());
                preferences.setSetupState(SetupState.SETUP_FINISHED);
                return new SendMessage()
                        .setReplyMarkup(getInlineKeyboard())
                        .setChatId(message.getChatId())
                        .setText(printUserInfo(preferences));
            case CHANGE_PASSWORD:
                preferences.setPassword(message.getText());
                preferences.setSetupState(SetupState.SETUP_FINISHED);
                return new SendMessage()
                        .setReplyMarkup(getInlineKeyboard())
                        .setChatId(message.getChatId())
                        .setText(printUserInfo(preferences));
        }
        preferences.setSetupState(SetupState.SETUP_FINISHED);

        return new EditMessageText()
                .setReplyMarkup(getInlineKeyboard())
                .setChatId(message.getChatId())
                .setMessageId(toIntExact(message.getMessageId()))
                .setText(printUserInfo(preferences));
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
//        rowInline.add(new InlineKeyboardButton().setText("Remove account").setCallbackData("removeAccount"));
        rowsInline.add(rowInline);
        markupInline.setKeyboard(rowsInline);
        return markupInline;
    }

    private String printUserInfo(Preferences preferences) {
        return EmojiParser.parseToUnicode(new StringBuilder().append(":leaves:")
                .append("Current account settings:fallen_leaf: \n")
                .append(":e-mail:")
                .append("Email: ")
                .append(preferences.getEmail())
                .append("\n")
                .append(":lock:Password: ***** \n")
                .append(":rocket:Vendor: ")
                .append(preferences.getVendor().name())
                .append("\n")
                .append(":rotating_light:Protocol: ")
                .append(preferences.getProtocol().name())
                .toString());
    }
}
